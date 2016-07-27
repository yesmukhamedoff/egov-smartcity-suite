<%--
  ~ eGov suite of products aim to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) <2015>  eGovernments Foundation
  ~
  ~     The updated version of eGov suite of products as by eGovernments Foundation
  ~     is available at http://www.egovernments.org
  ~
  ~     This program is free software: you can redistribute it and/or modify
  ~     it under the terms of the GNU General Public License as published by
  ~     the Free Software Foundation, either version 3 of the License, or
  ~     any later version.
  ~
  ~     This program is distributed in the hope that it will be useful,
  ~     but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~     GNU General Public License for more details.
  ~
  ~     You should have received a copy of the GNU General Public License
  ~     along with this program. If not, see http://www.gnu.org/licenses/ or
  ~     http://www.gnu.org/licenses/gpl.html .
  ~
  ~     In addition to the terms of the GPL license to be adhered to in using this
  ~     program, the following additional terms are to be complied with:
  ~
  ~         1) All versions of this program, verbatim or modified must carry this
  ~            Legal Notice.
  ~
  ~         2) Any misrepresentation of the origin of the material is prohibited. It
  ~            is required that all modified versions of this material be marked in
  ~            reasonable ways as different from the original version.
  ~
  ~         3) This license does not grant any rights to any user of the program
  ~            with regards to rights under trademark law for use of the trade names
  ~            or trademarks of eGovernments Foundation.
  ~
  ~   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
  --%>
<c:if test="${!details.measurementSheets.isEmpty() }">
	<!--only for validity head start -->                         
    <table>
	    <tr>
	        <td colspan="15"><!--only for validity head end -->
	            <div class="view-content">Measurement Sheet <div class="pull-right"><span class="glyphicon glyphicon-remove-circle error-msg hide-ms" style="cursor:pointer;font-size:16px;"></span></div>
	            <table class=" table table-bordered" id="nonSorMbDetails[${item.index }].mstable">
	                <thead>
	                	<tr>
		                    <th colspan="1"></th>
		                    <th colspan="7">Estimated</th>
		                    <th colspan="6">Actuals</th>
	                    </tr>
	                    <tr>
		                    <th>S.no</th>
		                    <th>Description</th>
		                    <th>Number</th>
		                    <th>Length</th>
		                    <th>Width</th>
		                    <th>Depth/Height</th>
		                    <th>Quantity</th>
		                    <th>Deduction</th>
		                    <th>Remarks</th>
		                    <th>Number</th>
		                    <th>Length</th>
		                    <th>Width</th>
		                    <th>Depth/Height</th>
		                    <th>Quantity</th>
	                    </tr>
	                </thead>
	                <tbody>
	                	<c:forEach items="${details.measurementSheets}" var="mbms" varStatus="index">
	                		<tr>
			                	<td hidden="true">
			                        <form:input path="nonSorMbDetails[${item.index }].measurementSheets[${index.index }].id" id="nonSorMbDetails_${item.index }_measurementSheets_${index.index }_id" />
			                        <form:input path="nonSorMbDetails[${item.index }].measurementSheets[${index.index }].woMeasurementSheet" id="nonSorMbDetails_${item.index }_measurementSheets_${index.index }_woMeasurementSheet" />
			                    </td>
			                	<td id="nonsormsrowslNo_${item.index }_${index.index }">${mbms.woMeasurementSheet.measurementSheet.slNo }</td>
			                    <td id="nonsormsrowremarks_${item.index }_${index.index }">${mbms.woMeasurementSheet.measurementSheet.remarks }</td>
			                    <td id="nonsormsrowno_${item.index }_${index.index }">${mbms.woMeasurementSheet.no }</td>
			                    <td id="nonsormsrowlength_${item.index }_${index.index }">${mbms.woMeasurementSheet.length }</td>
			                    <td id="nonsormsrowwidth_${item.index }_${index.index }">${mbms.woMeasurementSheet.width }</td>
			                    <td id="nonsormsrowdepthOrHeight_${item.index }_${index.index }">${mbms.woMeasurementSheet.depthOrHeight }</td>
			                    <td id="nonsormsrowquantity_${item.index }_${index.index }">${mbms.woMeasurementSheet.quantity }</td>
			                    <c:if test="${mbms.woMeasurementSheet.measurementSheet.identifier == 'A'}">
									<c:set var="total" value="${total + mbms.woMeasurementSheet.quantity}" />
								</c:if>
								<c:if test="${mbms.woMeasurementSheet.measurementSheet.identifier == 'D'}">
									<c:set var="total" value="${total - mbms.woMeasurementSheet.quantity}" />
								</c:if>
			                    <td id="nonsormsrowidentifier_${item.index }_${index.index }">
			                    	<c:choose>
			                    		<c:when test="${mbms.woMeasurementSheet.measurementSheet.identifier == 'A'}">No</c:when>
			                    		<c:otherwise>Yes</c:otherwise>
			                    	</c:choose>
			                    </td>
			                    <td>
			                        <form:textarea path="nonSorMbDetails[${item.index }].measurementSheets[${index.index }].remarks" id="nonSorMbDetails[${item.index }].measurementSheets[${index.index }].remarks" class="form-control text-left patternvalidation runtime-update"
			                               data-pattern="alphanumeric" maxlength="1024" ></form:textarea>
			
			                    </td>
			                    <td>
			                        <form:input path="nonSorMbDetails[${item.index }].measurementSheets[${index.index }].no"  id="nonSorMbDetails[${item.index }].measurementSheets[${index.index }].no" class="form-control text-right patternvalidation runtime-update"
			                               data-pattern="decimalvalue" data-idx="0" />
			
			                    </td>
			                    <td>                                                                     
			                        <form:input path="nonSorMbDetails[${item.index }].measurementSheets[${index.index }].length" id="nonSorMbDetails[${item.index }].measurementSheets[${index.index }].length" class="form-control text-right patternvalidation runtime-update"
			                               data-pattern="decimalvalue" data-idx="0" />
			
			                    </td>
			                    <td>
			                        <form:input path="nonSorMbDetails[${item.index }].measurementSheets[${index.index }].width" id="nonSorMbDetails[${item.index }].measurementSheets[${index.index }].width" class="form-control text-right patternvalidation runtime-update"
			                               data-pattern="decimalvalue"  data-idx="0" />
			
			                    </td>
			                    <td>
				                    <form:input path="nonSorMbDetails[${item.index }].measurementSheets[${index.index }].depthOrHeight" id="nonSorMbDetails[${item.index }].measurementSheets[${index.index }].depthOrHeight" class="form-control text-right patternvalidation runtime-update"
				                           data-pattern="decimalvalue"  data-idx="0" />
			
			                	</td>
			                	<td>
				                    <form:input path="nonSorMbDetails[${item.index }].measurementSheets[${index.index }].quantity" id="nonSorMbDetails[${item.index }].measurementSheets[${index.index }].quantity" class="form-control text-right patternvalidation runtime-update"
				                           data-pattern="decimalvalue" onblur="findNet(this)" />
									<c:if test="${mbms.woMeasurementSheet.measurementSheet.identifier == 'A'}">
										<c:set var="net" value="${net + mbms.quantity}" />
									</c:if>
									<c:if test="${mbms.woMeasurementSheet.measurementSheet.identifier == 'D'}">
										<c:set var="net" value="${net - mbms.quantity}" />
									</c:if>
			                	</td>
			                </tr>
	                	</c:forEach>
		                <tr>
		                	<td colspan="5"></td>
		                    <td class="text-right">Total</td>
		                    <td class="text-right view-content">${total }</td>
		                    <td colspan="4" class="text-right">
		                        <button name="resetButton" id="resetButton" class="btn btn-xs btn-danger">Reset</button>
		                        <input type="button" value="Submit" id="nonSorMbDetails[${item.index }].mssubmit" class="btn btn-xs btn-primary ms-submit mssubmit_${item.index }"/> 
		                    </td>
		                    <td class="text-right">Total</td>
		                    <td id="nonSorMbDetails[${item.index }].msnet" class="text-right">${net}</td>
		                    <td></td>
		                </tr>
	                </tbody>
	            </table>
	        <!--only for validity tail start -->  
	        </td>
        </tr><!--only for validity -->
    </table> <!--only for validity tail end -->
</c:if>