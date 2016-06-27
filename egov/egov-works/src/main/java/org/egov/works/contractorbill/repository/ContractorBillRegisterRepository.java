/*
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
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
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
 */
package org.egov.works.contractorbill.repository;

import java.math.BigDecimal;
import java.util.List;

import org.egov.works.contractorbill.entity.ContractorBillRegister;
import org.egov.works.workorder.entity.WorkOrder;
import org.egov.works.workorder.entity.WorkOrderEstimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractorBillRegisterRepository extends JpaRepository<ContractorBillRegister, Long> {

    ContractorBillRegister findByBillnumber(final String billNumber);

    List<ContractorBillRegister> findByBillnumberContainingIgnoreCase(final String billNumber);

    @Query("select max(billSequenceNumber) from ContractorBillRegister cbr where cbr.workOrderEstimate.estimate.projectCode.code = :workIdentificationNumber")
    Integer findMaxBillSequenceNumberByWorkOrder(@Param("workIdentificationNumber") String workIdentificationNumber);

    @Query("select distinct(cbr.workOrderEstimate.estimate.projectCode.code) from ContractorBillRegister as cbr  where upper(cbr.workOrderEstimate.estimate.projectCode.code) like upper(:code) ")
    List<String> findWorkIdentificationNumberToSearchContractorBill(@Param("code") String code);

    @Query("select distinct(cbr.workOrderEstimate.workOrder.contractor.name) from ContractorBillRegister as cbr where upper(cbr.workOrderEstimate.workOrder.contractor.name) like upper(:contractorname) or upper(cbr.workOrderEstimate.workOrder.contractor.code) like upper(:contractorname)  and cbr.workOrderEstimate.workOrder.egwStatus.code = :workOrderStatus ")
    List<String> findContractorForContractorBill(@Param("contractorname") String contractorname,
            @Param("workOrderStatus") String workOrderStatus);

    @Query("select sum(cbr.billamount) from ContractorBillRegister as cbr where cbr.workOrderEstimate = :workOrderEstimate and upper(cbr.billstatus) not in (:billStatus)")
    BigDecimal findSumOfBillAmountByWorkOrderAndStatus(@Param("workOrderEstimate") final WorkOrderEstimate workOrderEstimate,
            @Param("billStatus") final String billStatus);

    @Query("select sum(cbr.billamount) from ContractorBillRegister as cbr where cbr.workOrderEstimate = :workOrderEstimate and cbr.id != :id and upper(cbr.billstatus) not in (:billStatus)")
    BigDecimal findSumOfBillAmountByWorkOrderAndStatusAndNotContractorBillRegister(
            @Param("workOrderEstimate") final WorkOrderEstimate workOrderEstimate,
            @Param("billStatus") final String billStatus, @Param("id") Long id);

    @Query("select distinct(cbr.workOrderEstimate.workOrder.workOrderNumber) from ContractorBillRegister as cbr where cbr.workOrderEstimate.workOrder.workOrderNumber like :workOrderNumber and upper(cbr.billstatus) != :status")
    List<String> findWorkOrderNumbersToCancel(@Param("workOrderNumber") String workOrderNumber, @Param("status") String status);

    @Query("select distinct(cbr.workOrderEstimate.estimate.projectCode.code) from ContractorBillRegister as cbr where upper(cbr.workOrderEstimate.estimate.projectCode.code) like upper(:code) and billstatus = :status ")
    List<String> findWorkIdentificationNumberToSearchContractorBillToCancel(@Param("code") String code,
            @Param("status") String status);

    @Query("select distinct(cbr.billnumber) from ContractorBillRegister as cbr where upper(cbr.billnumber) like upper(:billnumber) and billstatus = :status")
    List<String> findBillNumberToSearchContractorBillToCancel(@Param("billnumber") String billNumber,
            @Param("status") String status);

    @Query("select distinct(cbr) from ContractorBillRegister as cbr where cbr.workOrderEstimate.workOrder = :workOrder and upper(cbr.billstatus) != :billstatus ")
    List<ContractorBillRegister> findByWorkOrderAndBillstatusNot(@Param("workOrder") WorkOrder workOrder,
            @Param("billstatus") final String billstatus);

    @Query("select distinct(cbr) from ContractorBillRegister as cbr where cbr.workOrderEstimate = :workOrderEstimate and upper(cbr.billstatus) != :status and cbr.billtype = :billtype")
    ContractorBillRegister findByWorkOrderAndBillTypeAndStatus(
            @Param("workOrderEstimate") final WorkOrderEstimate workOrderEstimate, @Param("status") final String status,
            @Param("billtype") final String billtype);

    @Query("select distinct(cbr.workOrderEstimate.workOrder.contractor.name) from ContractorBillRegister as cbr where upper(cbr.workOrderEstimate.workOrder.contractor.name) like upper(:contractorname) or upper(cbr.workOrderEstimate.workOrder.contractor.code) like upper(:contractorname)  and cbr.workOrderEstimate.workOrder.egwStatus.code in (:status1,:status2) ")
    List<String> findContractorByWorkOrderStatus(@Param("contractorname") String contractorname,
            @Param("status1") String status1, @Param("status2") String status2);
}
