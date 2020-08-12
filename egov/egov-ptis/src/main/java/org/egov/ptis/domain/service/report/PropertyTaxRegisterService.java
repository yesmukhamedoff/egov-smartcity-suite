/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2020  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 *
 */
package org.egov.ptis.domain.service.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.demand.model.EgDemandDetails;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.config.persistence.datasource.routing.annotation.ReadOnly;
import org.egov.infra.reporting.engine.ReportFormat;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.reporting.engine.ReportRequest;
import org.egov.infra.reporting.engine.ReportService;
import org.egov.infra.utils.DateUtils;
import org.egov.ptis.client.service.calculator.APTaxCalculator;
import org.egov.ptis.constants.PropertyTaxConstants;
import org.egov.ptis.domain.dao.property.PropertyHibernateDAO;
import org.egov.ptis.domain.entity.demand.Ptdemand;
import org.egov.ptis.domain.entity.property.BasicProperty;
import org.egov.ptis.domain.entity.property.Floor;
import org.egov.ptis.domain.entity.property.Property;
import org.egov.ptis.domain.entity.property.PropertyDetail;
import org.egov.ptis.domain.model.reportregister.PropertyTaxRegisterBean;
import org.egov.ptis.domain.model.reportregister.RevisedAssessmentDetailsBean;
import org.egov.ptis.domain.model.reportregister.StoreyDetailsRegisterBean;
import org.egov.ptis.domain.model.reportregister.TaxDetailsBean;
import org.egov.ptis.domain.service.notice.NoticeService;
import org.egov.ptis.notice.PtNotice;
import org.egov.ptis.service.utils.PropertyTaxCommonUtils;
import org.hibernate.Query;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class PropertyTaxRegisterService {

    private final static String NA = "NA";
    private final static String YES = "Yes";
    private final static String NO = "No";
    private final static String EQUALS = "= 'VAC_LAND'";
    private final static String NOT_EQUALS = "<> 'VAC_LAND'";

    @Autowired
    private ReportService reportService;

    @Autowired
    private CityService cityService;

    @Autowired
    private PropertyHibernateDAO propertyHibernateDAO;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private APTaxCalculator apTaxCalculator;

    @Autowired
    private PropertyTaxCommonUtils propertyTaxCommonUtils;

    public Integer getYearFromYearMonth(String yearMonth) {
        String year = yearMonth.substring(4);
        return Integer.valueOf(year);
    }

    public Integer getMonthFromYearMonth(String yearMonth) {
        String month = yearMonth.substring(0, 3);
        DateTimeFormatter format = DateTimeFormat.forPattern("MMM");
        DateTime instance = format.parseDateTime(month);
        return instance.getMonthOfYear();
    }

    @ReadOnly
    public ReportOutput generatePropertyTaxRegister(String yearMonth, Long wardId, String mode) {
        ReportOutput reportOutput = null;
        final Map<String, Object> reportParams = new HashMap<>();
        ReportRequest reportInput;
        List<PropertyTaxRegisterBean> propertyTaxRegisterList = new ArrayList<>();
        List<Property> propertyList = getApprovedPropertiesByMonthAndYear(getYearFromYearMonth(yearMonth),
                getMonthFromYearMonth(yearMonth), wardId, mode);
        if (!propertyList.isEmpty()) {
            for (Property property : propertyList) {
                propertyTaxRegisterList.add(mode.equals(PropertyTaxConstants.CATEGORY_TYPE_PROPERTY_TAX)
                        ? prepareTaxRegisterDetails(property) : prepareVLTRegisterDetails(property));
            }
        }
        reportParams.put("propertyTaxRegisterList", propertyTaxRegisterList);
        reportParams.put("yearMonth", yearMonth);
        reportParams.put("cityName", cityService.getMunicipalityName());
        reportParams.put("cityGrade", cityService.getCityGrade());
        if (mode.equals(PropertyTaxConstants.CATEGORY_TYPE_PROPERTY_TAX))
            reportInput = new ReportRequest(PropertyTaxConstants.REPORT_PT_TAX_REGISTER, reportParams, reportParams);
        else
            reportInput = new ReportRequest(PropertyTaxConstants.REPORT_VLT_TAX_REGISTER, reportParams, reportParams);
        reportInput.setPrintDialogOnOpenReport(true);
        reportInput.setReportFormat(ReportFormat.PDF);
        reportOutput = reportService.createReport(reportInput);
        return reportOutput;
    }

    private PropertyTaxRegisterBean prepareTaxRegisterDetails(Property property) {
        PropertyTaxRegisterBean propertyTaxRegister = new PropertyTaxRegisterBean();
        final BasicProperty basicProperty = property.getBasicProperty();
        propertyTaxRegister.setRevisedAssessmentDetails(prepareRevisedAssessmentDetails(property));
        propertyTaxRegister.setWard(basicProperty.getPropertyID().getWard().getName());
        propertyTaxRegister.setZone(basicProperty.getPropertyID().getZone().getName());
        propertyTaxRegister.setAssessmentNo(basicProperty.getUpicNo());
        propertyTaxRegister.setLocality(basicProperty.getPropertyID().getLocality().getName());
        propertyTaxRegister.setDoorNo(basicProperty.getAddress().getHouseNoBldgApt());
        propertyTaxRegister.setOwnerName(basicProperty.getFullOwnerName());
        propertyTaxRegister.setOwnerAddress(basicProperty.getAddress().toString());
        propertyTaxRegister.setStoreyDetails(prepareStoreyDetails(property));
        return propertyTaxRegister;
    }

    private RevisedAssessmentDetailsBean prepareRevisedAssessmentDetails(Property property) {
        RevisedAssessmentDetailsBean revisedAssessmentDetails = new RevisedAssessmentDetailsBean();
        final PropertyDetail propertyDetail = property.getPropertyDetail();
        revisedAssessmentDetails.setApplicationType(property.getPropertyModifyReason());
        revisedAssessmentDetails.setEffectiveDate(propertyDetail.getFloorDetails().stream()
                .map(fd -> fd.getOccupancyDate()).min(Date::compareTo).get());
        revisedAssessmentDetails.setConstructionDate(propertyDetail.getFloorDetails().stream()
                .map(fd -> fd.getConstructionDate()).min(Date::compareTo).get());
        revisedAssessmentDetails.setDemoltionEffectiveDate(
                property.getPropertyModifyReason().equals(PropertyTaxConstants.DEMOLITION) ? property.getEffectiveDate() : null);
        revisedAssessmentDetails
                .setRoofType(propertyDetail.getRoofType() != null ? propertyDetail.getRoofType().getName() : NA);
        revisedAssessmentDetails
                .setWallType(propertyDetail.getWallType() != null ? propertyDetail.getWallType().getName() : NA);
        revisedAssessmentDetails
                .setWoodType(propertyDetail.getWoodType() != null ? propertyDetail.getWoodType().getName() : NA);
        revisedAssessmentDetails
                .setFloorType(propertyDetail.getFloorType() != null ? propertyDetail.getFloorType().getName() : NA);
        revisedAssessmentDetails.setElectricity(propertyDetail.isElectricity() ? YES : NO);
        revisedAssessmentDetails.setAttachedBathroom(propertyDetail.isAttachedBathRoom() ? YES : NO);
        revisedAssessmentDetails.setWaterTap(propertyDetail.isWaterTap() ? YES : NO);
        setNoticeDetails(revisedAssessmentDetails, property);
        revisedAssessmentDetails.setRevisedTaxDetails(prepareTaxDetails(property));
        return revisedAssessmentDetails;
    }

    @ReadOnly
    private void setNoticeDetails(RevisedAssessmentDetailsBean revisedAssessmentDetails,
            Property property) {
        PtNotice notice = noticeService.getNoticeByApplicationNumber(property.getApplicationNo());
        if (notice != null) {
            revisedAssessmentDetails.setSpecialNotice(notice.getNoticeNo());
            revisedAssessmentDetails.setSpecialNoticeDate(notice.getNoticeDate());
        }
    }

    private List<StoreyDetailsRegisterBean> prepareStoreyDetails(Property property) {
        List<StoreyDetailsRegisterBean> storeyDetailsList = new ArrayList<>();
        for (Floor floor : property.getPropertyDetail().getFloorDetails()) {
            StoreyDetailsRegisterBean storeyDetails = new StoreyDetailsRegisterBean();
            storeyDetails.setUsage(floor.getPropertyUsage().getUsageName());
            storeyDetails.setOccupation(floor.getPropertyOccupation().getOccupation());
            storeyDetails.setConstructionType(floor.getStructureClassification().getTypeName());
            storeyDetails
                    .setPlinthArea(BigDecimal.valueOf(floor.getBuiltUpArea().getArea()).setScale(2, BigDecimal.ROUND_HALF_UP));
            storeyDetails.setMrv(floor.getFloorDmdCalc().getMrv().setScale(2, BigDecimal.ROUND_HALF_UP));
            storeyDetails.setUnitRate(floor.getFloorDmdCalc().getCategoryAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
            storeyDetails.setTotalNetARV(floor.getFloorDmdCalc().getAlv().setScale(2, BigDecimal.ROUND_HALF_UP));
            storeyDetails.setBuildingARVBeforeDepriciation(
                    apTaxCalculator.calculateFloorBuildingValue(storeyDetails.getMrv()).multiply(new BigDecimal(12)).setScale(2,
                            BigDecimal.ROUND_HALF_UP));
            storeyDetails.setAge(DateUtils.noOfYearsBetween(floor.getConstructionDate(), floor.getOccupancyDate()));
            storeyDetails.setDepriciationValue(
                    apTaxCalculator.calculateFloorDepreciation(storeyDetails.getBuildingARVBeforeDepriciation(), floor)
                            .setScale(2, BigDecimal.ROUND_HALF_UP));
            storeyDetails.setBuildingARV(
                    storeyDetails.getBuildingARVBeforeDepriciation().subtract(storeyDetails.getDepriciationValue()).setScale(2,
                            BigDecimal.ROUND_HALF_UP));
            storeyDetails
                    .setSitalARV(apTaxCalculator.calculateFloorSiteValue(storeyDetails.getMrv()).multiply(new BigDecimal(12))
                            .setScale(2, BigDecimal.ROUND_HALF_UP));
            storeyDetailsList.add(storeyDetails);
        }
        return storeyDetailsList;
    }

    @ReadOnly
    private TaxDetailsBean prepareTaxDetails(Property property) {
        TaxDetailsBean taxDetails = new TaxDetailsBean();
        BigDecimal general = BigDecimal.ZERO;
        BigDecimal waterAndDrainage = BigDecimal.ZERO;
        BigDecimal scavenge = BigDecimal.ZERO;
        BigDecimal light = BigDecimal.ZERO;
        BigDecimal education = BigDecimal.ZERO;
        BigDecimal libraryCess = BigDecimal.ZERO;
        BigDecimal unauthorizedPenalty = BigDecimal.ZERO;
        final Ptdemand ptdemand = propertyHibernateDAO.getLatestDemand(property);
        for (EgDemandDetails demandDetails : ptdemand.getEgDemandDetails()) {
            String demandReasonMaster = demandDetails.getEgDemandReason().getEgDemandReasonMaster().getCode();
            if (demandReasonMaster.equals(PropertyTaxConstants.DEMANDRSN_CODE_GENERAL_TAX)
                    || demandReasonMaster.equals(PropertyTaxConstants.DEMANDRSN_CODE_VACANT_TAX))
                general = general.add(propertyTaxCommonUtils.getTotalDemandVariationAmount(demandDetails));
            if (demandReasonMaster.equals(PropertyTaxConstants.DEMANDRSN_CODE_WATER_TAX) ||
                    demandReasonMaster.equals(PropertyTaxConstants.DEMANDRSN_CODE_DRAINAGE_TAX))
                waterAndDrainage = waterAndDrainage.add(propertyTaxCommonUtils.getTotalDemandVariationAmount(demandDetails));
            if (demandReasonMaster.equals(PropertyTaxConstants.DEMANDRSN_CODE_SCAVENGE_TAX))
                scavenge = scavenge.add(propertyTaxCommonUtils.getTotalDemandVariationAmount(demandDetails));
            if (demandReasonMaster.equals(PropertyTaxConstants.DEMANDRSN_CODE_LIGHT_TAX))
                light = light.add(propertyTaxCommonUtils.getTotalDemandVariationAmount(demandDetails));
            if (demandReasonMaster.equals(PropertyTaxConstants.DEMANDRSN_CODE_EDUCATIONAL_TAX))
                education = education.add(propertyTaxCommonUtils.getTotalDemandVariationAmount(demandDetails));
            if (demandReasonMaster.equals(PropertyTaxConstants.DEMANDRSN_CODE_LIBRARY_CESS))
                libraryCess = libraryCess.add(propertyTaxCommonUtils.getTotalDemandVariationAmount(demandDetails));
            if (demandReasonMaster.equals(PropertyTaxConstants.DEMANDRSN_CODE_UNAUTHORIZED_PENALTY))
                unauthorizedPenalty = unauthorizedPenalty
                        .add(propertyTaxCommonUtils.getTotalDemandVariationAmount(demandDetails));
        }
        taxDetails.setGeneral(general.setScale(2, BigDecimal.ROUND_HALF_UP));
        taxDetails.setWaterAndDrainage(waterAndDrainage.setScale(2, BigDecimal.ROUND_HALF_UP));
        taxDetails.setScavenge(scavenge.setScale(2, BigDecimal.ROUND_HALF_UP));
        taxDetails.setLight(light.setScale(2, BigDecimal.ROUND_HALF_UP));
        taxDetails.setEducation(education.setScale(2, BigDecimal.ROUND_HALF_UP));
        taxDetails.setLibraryCess(libraryCess.setScale(2, BigDecimal.ROUND_HALF_UP));
        taxDetails.setUnauthorizedPenalty(unauthorizedPenalty.setScale(2, BigDecimal.ROUND_HALF_UP));
        taxDetails.setPropertyTax(
                general.add(waterAndDrainage).add(scavenge).add(light).add(education).setScale(2, BigDecimal.ROUND_HALF_UP));
        taxDetails.setTotalTax(
                taxDetails.getPropertyTax().add(libraryCess).add(unauthorizedPenalty).setScale(2, BigDecimal.ROUND_HALF_UP));
        return taxDetails;
    }

    @SuppressWarnings("unchecked")
    @ReadOnly
    public List<Property> getApprovedPropertiesByMonthAndYear(Integer year, Integer month, Long wardId, String mode) {
        final Query query = propertyTaxCommonUtils.getSession().createQuery(
                "select p from PropertyImpl p, BasicPropertyImpl bp where p.status in ('A', 'H', 'I') and EXTRACT(year FROM p.state.lastModifiedDate) = :year and EXTRACT(month FROM p.state.lastModifiedDate) = :month "
                        + " and bp.source = 'A' and bp.active = true and bp.propertyID.ward = :wardId and p.basicProperty = bp.id and p.propertyDetail.propertyTypeMaster.code "
                        + getPropertyType(mode) + " order by p.id asc ");
        query.setInteger("year", year);
        query.setInteger("month", month);
        query.setLong("wardId", wardId);
        final List<Property> properties = query.list();
        return properties;
    }

    public String getPropertyType(final String mode) {
        return mode.equals(PropertyTaxConstants.CATEGORY_TYPE_VACANTLAND_TAX)
                ? EQUALS : NOT_EQUALS;
    }

    public PropertyTaxRegisterBean prepareVLTRegisterDetails(Property property) {
        PropertyTaxRegisterBean vltRegister = new PropertyTaxRegisterBean();
        final BasicProperty basicProperty = property.getBasicProperty();
        vltRegister.setAssessmentNo(basicProperty.getUpicNo());
        vltRegister.setOwnerName(basicProperty.getFullOwnerName());
        vltRegister.setOwnerAddress(basicProperty.getAddress().toString());
        vltRegister.setPattaNo(property.getPropertyDetail().getPattaNumber());
        vltRegister.setSurveyNo(property.getPropertyDetail().getSurveyNumber());
        vltRegister.setRevisedAssessmentDetails(prepareRevisedAssessmentDetailsVLT(property));
        return vltRegister;
    }
    
    private RevisedAssessmentDetailsBean prepareRevisedAssessmentDetailsVLT(Property property) {
        RevisedAssessmentDetailsBean revisedAssessmentDetailsVLT = new RevisedAssessmentDetailsBean();
        setNoticeDetails(revisedAssessmentDetailsVLT, property);
        revisedAssessmentDetailsVLT.setRevisedTaxDetails(prepareTaxDetails(property));
        revisedAssessmentDetailsVLT.getRevisedTaxDetails()
                .setCapitalOrARValue(property.getPropertyDetail().getCurrentCapitalValue());
        revisedAssessmentDetailsVLT.getRevisedTaxDetails()
                .setLandArea(BigDecimal.valueOf(property.getPropertyDetail().getSitalArea().getArea()));
        revisedAssessmentDetailsVLT.setApplicationType(property.getPropertyModifyReason());
        return revisedAssessmentDetailsVLT;
    }
}