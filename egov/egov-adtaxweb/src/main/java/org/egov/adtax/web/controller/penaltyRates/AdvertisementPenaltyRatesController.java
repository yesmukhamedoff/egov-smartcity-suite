/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
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

package org.egov.adtax.web.controller.penaltyRates;

import org.egov.adtax.entity.AdvertisementPenaltyRates;
import org.egov.adtax.search.contract.HoardingPenaltyRates;
import org.egov.adtax.service.AdvertisementPenaltyRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "penalty")
public class AdvertisementPenaltyRatesController {

    @Autowired
    private AdvertisementPenaltyRatesService penaltyRatesService;

    @RequestMapping(value = "/change")
    public String changePenaltyRates(@ModelAttribute HoardingPenaltyRates hoardingPenaltyRates, final Model model) {
        hoardingPenaltyRates.setAdvtPenaltyRatesList(penaltyRatesService.findPenaltyRatesInAscendingOrder());
        return "penaltyRates-change";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createPenaltyRates(@ModelAttribute HoardingPenaltyRates hoardingPenaltyRates,
            final RedirectAttributes redirectAttributes, @RequestParam final Long id) {
        List<AdvertisementPenaltyRates> rateList = new ArrayList<AdvertisementPenaltyRates>();
        rateList = penaltyRatesService.findPenaltyRatesInAscendingOrder();

        if (hoardingPenaltyRates != null) {

            for (AdvertisementPenaltyRates advtPenaltyRates : hoardingPenaltyRates.getAdvtPenaltyRatesList()) {
                penaltyRatesService.createPenaltyRates(advtPenaltyRates);
            }

            for (AdvertisementPenaltyRates rate : rateList) {
                if (!hoardingPenaltyRates.getAdvtPenaltyRatesList().contains(rate)) {
                    penaltyRatesService.delete(rate);
                }
            }
        }
        return "penaltyRate-success";
    }
}