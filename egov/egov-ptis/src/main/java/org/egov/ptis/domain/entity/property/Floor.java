/*******************************************************************************
 * eGov suite of products aim to improve the internal efficiency,transparency, 
 *    accountability and the service delivery of the government  organizations.
 * 
 *     Copyright (C) <2015>  eGovernments Foundation
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
 * 	1) All versions of this program, verbatim or modified must carry this 
 * 	   Legal Notice.
 * 
 * 	2) Any misrepresentation of the origin of the material is prohibited. It 
 * 	   is required that all modified versions of this material be marked in 
 * 	   reasonable ways as different from the original version.
 * 
 * 	3) This license does not grant any rights to any user of the program 
 * 	   with regards to rights under trademark law for use of the trade names 
 * 	   or trademarks of eGovernments Foundation.
 * 
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org
 ******************************************************************************/
/*
 * FloorIF.java Created on Oct 25, 2005
 *
 * Copyright 2005 eGovernments Foundation. All rights reserved.
 * EGOVERNMENTS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.egov.ptis.domain.entity.property;

import java.math.BigDecimal;
import java.util.Date;

import org.egov.commons.Area;
//import org.egov.demand.model.DepreciationMaster;
import org.egov.demand.model.DepreciationMaster;
import org.egov.exceptions.EGOVRuntimeException;
import org.egov.infra.persistence.entity.Auditable;
import org.egov.infstr.models.BaseModel;

/**
 * <p>
 * This is an Interface which describes the Floor Details of a Property. A
 * Property might have one or more floors. The data for property tax might be
 * given for individual floors or at aggregate level.
 * </p>
 *
 * @author Gayathri Joshi
 * @version 2.00
 * @see org.egov.ptis.domain.entity.property.Floor
 * @since 2.00
 */

public class Floor extends BaseModel implements Auditable {
	private ConstructionTypeSet constructionTypeSet = null;
	private StructureClassification structureClassification = null;
	private PropertyUsage propertyUsage = null;
	private PropertyOccupation propertyOccupation = null;
	private DepreciationMaster depreciationMaster = null;
	private Integer floorNo = null;
	private Area builtUpArea = null;
	private Area floorArea = null;
	private String waterMeter = null;
	private String electricMeter = null;
	private PropertyTypeMaster unitType;
	private String unitTypeCategory;
	private BigDecimal rentPerMonth;
	private BigDecimal manualAlv;
	private BigDecimal alv;
	private String waterRate;
	private PropertyDetail propertyDetail;
	private Date occupancyDate;
	private String occupantName;

	// This field contains Unit No For NMC Impl
	private String extraField1;
	// This field contains Occupier Name For NMC Impl
	private String extraField2;
	// This field contains Occupation Date For NMC Impl
	private String extraField3;
	// This field contains Width For NMC Impl
	private String extraField4;
	// This field contains Length For NMC Impl
	private String extraField5;
	// This field contains Intercepting Wall Area For NMC Impl
	private String extraField6;
	// This field contains floor Type
	private String extraField7;

	private String taxExemptedReason;

	// private UnitRentAgreementDetail rentAgreementDetail;

	private String capitalValue;
	private boolean planApproved;

	public Floor(ConstructionTypeSet constructionTypeSet,
			StructureClassification structureClassification, PropertyUsage propertyUsage,
			PropertyOccupation propertyOccupation, Integer floorNo,
			DepreciationMaster depreciationMaster, Area builtUpArea, Area floorArea,
			String waterMeter, String electricMeter, Date lastUpdatedTimeStamp,
			Date createdTimeStamp, BigDecimal rentPerMonth, String extraField1, String extraField2,
			String extraField3, String extraField4, String extraField5, String extraField6,
			String extraField7, BigDecimal manualAlv, PropertyTypeMaster unitType,
			String unitTypeCategory, String waterRate, BigDecimal alv, String taxExemptedReason,
			Date occupancyDate, String occupierName, String capitalValue, boolean planApproved) {
		super();
		this.constructionTypeSet = constructionTypeSet;
		this.structureClassification = structureClassification;
		this.propertyUsage = propertyUsage;
		this.propertyOccupation = propertyOccupation;
		this.floorNo = floorNo;
		this.depreciationMaster = depreciationMaster;
		this.builtUpArea = builtUpArea;
		this.floorArea = floorArea;
		this.waterMeter = waterMeter;
		this.electricMeter = electricMeter;

		this.rentPerMonth = rentPerMonth;
		this.extraField1 = extraField1;
		this.extraField2 = extraField2;
		this.extraField3 = extraField3;
		this.extraField4 = extraField4;
		this.extraField5 = extraField5;
		this.extraField6 = extraField6;
		this.extraField7 = extraField7;
		this.manualAlv = manualAlv;
		this.unitType = unitType;
		this.unitTypeCategory = unitTypeCategory;
		this.waterRate = waterRate;
		this.alv = alv;
		this.taxExemptedReason = taxExemptedReason;
		this.occupancyDate = occupancyDate;
		this.occupantName = occupierName;
		this.capitalValue = capitalValue;
		this.planApproved = planApproved;
	}

	public Floor() {

	}

	public Floor(ConstructionTypeSet constructionTypeSet,
			StructureClassification structureClassification, PropertyUsage propertyUsage,
			PropertyOccupation propertyOccupation, Integer floorNo,
			DepreciationMaster depreciationMaster, Area builtUpArea, Area floorArea,
			String waterMeter, String electricMeter) {
		this.constructionTypeSet = constructionTypeSet;
		this.structureClassification = structureClassification;
		this.propertyUsage = propertyUsage;
		this.propertyOccupation = propertyOccupation;
		this.floorNo = floorNo;
		this.depreciationMaster = depreciationMaster;
		this.builtUpArea = builtUpArea;
		this.floorArea = floorArea;
		this.waterMeter = waterMeter;
		this.electricMeter = electricMeter;
	}

	/**
	 * @return Returns the builtUpArea.
	 */

	public Area getBuiltUpArea() {
		return builtUpArea;
	}

	/**
	 * @param builtUpArea
	 *            The builtUpArea to set.
	 */

	public void setBuiltUpArea(Area builtUpArea) {
		this.builtUpArea = builtUpArea;
	}

	/**
	 * @return Returns the constructionTypeSet.
	 */

	public ConstructionTypeSet getConstructionTypeSet() {
		return constructionTypeSet;
	}

	/**
	 * @param constructionTypeSet
	 *            The constructionTypeSet to set.
	 */

	public void setConstructionTypeSet(ConstructionTypeSet constructionTypeSet) {
		this.constructionTypeSet = constructionTypeSet;
	}

	/**
	 * @return Returns the electricMeter.
	 */

	public String getElectricMeter() {
		return electricMeter;
	}

	/**
	 * @param electricMeter
	 *            The electricMeter to set.
	 */

	public void setElectricMeter(String electricMeter) {
		this.electricMeter = electricMeter;
	}

	public PropertyTypeMaster getUnitType() {
		return unitType;
	}

	public void setUnitType(PropertyTypeMaster unitType) {
		this.unitType = unitType;
	}

	public String getUnitTypeCategory() {
		return this.unitTypeCategory;
	}

	public void setUnitTypeCategory(String unitTypeCategory) {
		this.unitTypeCategory = unitTypeCategory;
	}

	/**
	 * @return Returns the floorArea.
	 */

	public Area getFloorArea() {
		return floorArea;
	}

	/**
	 * @param floorArea
	 *            The floorArea to set.
	 */

	public void setFloorArea(Area floorArea) {
		this.floorArea = floorArea;
	}

	/**
	 * @return Returns the floorNo.
	 */

	public Integer getFloorNo() {
		return floorNo;
	}

	/**
	 * @param floorNo
	 *            The floorNo to set.
	 */

	public void setFloorNo(Integer floorNo) {
		this.floorNo = floorNo;
	}

	/**
	 * @return Returns the propertyOccupation.
	 */

	public PropertyOccupation getPropertyOccupation() {
		return propertyOccupation;
	}

	/**
	 * @param propertyOccupation
	 *            The propertyOccupation to set.
	 */

	public void setPropertyOccupation(PropertyOccupation propertyOccupation) {
		this.propertyOccupation = propertyOccupation;
	}

	/**
	 * @return Returns the propertyUsage.
	 */

	public PropertyUsage getPropertyUsage() {
		return propertyUsage;
	}

	/**
	 * @param propertyUsage
	 *            The propertyUsage to set.
	 */

	public void setPropertyUsage(PropertyUsage propertyUsage) {
		this.propertyUsage = propertyUsage;
	}

	/**
	 * @return Returns the structureClassification.
	 */

	public StructureClassification getStructureClassification() {
		return structureClassification;
	}

	/**
	 * @param structureClassification
	 *            The structureClassification to set.
	 */

	public void setStructureClassification(StructureClassification structureClassification) {
		this.structureClassification = structureClassification;
	}

	/**
	 * @return Returns the waterMeter.
	 */

	public String getWaterMeter() {
		return waterMeter;
	}

	/**
	 * @param waterMeter
	 *            The waterMeter to set.
	 */

	public void setWaterMeter(String waterMeter) {
		this.waterMeter = waterMeter;
	}

	/**
	 * @return true if the given Object is equal to FloorImpl this will get
	 *         invoke when more than one object is adding to
	 *         collection(ex:Set,Map.)
	 */

	public boolean equals(Object that) {
		if (that == null)
			return false;

		if (this == that)
			return true;
		if (that.getClass() != this.getClass())
			return false;

		final Floor thatFlrImpl = (Floor) that;

		if (this.getId() != null && thatFlrImpl.getId() != null) {
			if (getId().equals(thatFlrImpl.getId())) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * @return Returns the hashCode
	 */

	public int hashCode() {
		int hashCode = 0;
		if (getId() != null) {
			hashCode += this.getId().hashCode();
		}
		return hashCode;
	}

	/**
	 * @return Returns the boolean after validating the current object
	 */

	public boolean validateFloor() {
		if (getFloorNo() == null)
			throw new EGOVRuntimeException(
					"In FloorImpl Validate : FloorNumber is Not Set, Please Check !!");

		return true;
	}

	public BigDecimal getRentPerMonth() {
		return rentPerMonth;
	}

	public void setRentPerMonth(BigDecimal rentPerMonth) {
		this.rentPerMonth = rentPerMonth;
	}

	public String getExtraField1() {
		return extraField1;
	}

	public void setExtraField1(String extraField1) {
		this.extraField1 = extraField1;
	}

	public String getExtraField2() {
		return extraField2;
	}

	public void setExtraField2(String extraField2) {
		this.extraField2 = extraField2;
	}

	public String getExtraField3() {
		return extraField3;
	}

	public void setExtraField3(String extraField3) {
		this.extraField3 = extraField3;
	}

	public String getExtraField4() {
		return extraField4;
	}

	public void setExtraField4(String extraField4) {
		this.extraField4 = extraField4;
	}

	public String getExtraField5() {
		return extraField5;
	}

	public void setExtraField5(String extraField5) {
		this.extraField5 = extraField5;
	}

	public String getExtraField6() {
		return extraField6;
	}

	public void setExtraField6(String extraField6) {
		this.extraField6 = extraField6;
	}

	public String getExtraField7() {
		return extraField7;
	}

	public void setExtraField7(String extraField7) {
		this.extraField7 = extraField7;
	}

	public DepreciationMaster getDepreciationMaster() {
		return depreciationMaster;
	}

	public void setDepreciationMaster(DepreciationMaster depreciationMaster) {
		this.depreciationMaster = depreciationMaster;
	}

	public BigDecimal getManualAlv() {
		return manualAlv;
	}

	public void setManualAlv(BigDecimal manualAlv) {
		this.manualAlv = manualAlv;
	}

	public String getWaterRate() {
		return waterRate;
	}

	public void setWaterRate(String waterRate) {
		this.waterRate = waterRate;
	}

	public BigDecimal getAlv() {
		return alv;
	}

	public void setAlv(BigDecimal alv) {
		this.alv = alv;
	}

	public String getTaxExemptedReason() {
		return taxExemptedReason;
	}

	public void setTaxExemptedReason(String taxExemptedReason) {
		this.taxExemptedReason = taxExemptedReason;
	}

	public String getCapitalValue() {
		return capitalValue;
	}

	public void setCapitalValue(String capitalValue) {
		this.capitalValue = capitalValue;
	}

	public boolean isPlanApproved() {
		return planApproved;
	}

	public void setPlanApproved(boolean planApproved) {
		this.planApproved = planApproved;
	}

	public Date getOccupancyDate() {
		return occupancyDate;
	}

	public void setOccupancyDate(Date occupancyDate) {
		this.occupancyDate = occupancyDate;
	}

	public String getOccupantName() {
		return occupantName;
	}

	public void setOccupantName(String occupantName) {
		this.occupantName = occupantName;
	}

	public String toString() {
		return new StringBuilder(256).append("FloorImpl [Id: ").append(getId())
				.append(", FloorNo=").append(getFloorNo()).append(", FloorArea=")
				.append(getFloorArea() != null ? getFloorArea().getArea() : "null")
				.append(", PropertyUsage=").append(getPropertyUsage()).append(", StructCl=")
				.append(getStructureClassification()).append(", Occupancy=")
				.append(getPropertyOccupation()).append(", Depreciation=")
				.append(getDepreciationMaster()).append(", WaterRate=").append(getWaterRate())
				.append(", alv=").append(getAlv()).append(", taxExemptedReason=")
				.append(taxExemptedReason).append("]").toString();

	}

	public PropertyDetail getPropertyDetail() {
		return propertyDetail;
	}

	public void setPropertyDetail(PropertyDetail propertyDetail) {
		this.propertyDetail = propertyDetail;
	}

}
