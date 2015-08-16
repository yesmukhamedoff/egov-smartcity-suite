<!-- #-------------------------------------------------------------------------------
# eGov suite of products aim to improve the internal efficiency,transparency, 
#    accountability and the service delivery of the government  organizations.
# 
#     Copyright (C) <2015>  eGovernments Foundation
# 
#     The updated version of eGov suite of products as by eGovernments Foundation 
#     is available at http://www.egovernments.org
# 
#     This program is free software: you can redistribute it and/or modify
#     it under the terms of the GNU General Public License as published by
#     the Free Software Foundation, either version 3 of the License, or
#     any later version.
# 
#     This program is distributed in the hope that it will be useful,
#     but WITHOUT ANY WARRANTY; without even the implied warranty of
#     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#     GNU General Public License for more details.
# 
#     You should have received a copy of the GNU General Public License
#     along with this program. If not, see http://www.gnu.org/licenses/ or 
#     http://www.gnu.org/licenses/gpl.html .
# 
#     In addition to the terms of the GPL license to be adhered to in using this
#     program, the following additional terms are to be complied with:
# 
# 	1) All versions of this program, verbatim or modified must carry this 
# 	   Legal Notice.
# 
# 	2) Any misrepresentation of the origin of the material is prohibited. It 
# 	   is required that all modified versions of this material be marked in 
# 	   reasonable ways as different from the original version.
# 
# 	3) This license does not grant any rights to any user of the program 
# 	   with regards to rights under trademark law for use of the trade names 
# 	   or trademarks of eGovernments Foundation.
# 
#   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
#------------------------------------------------------------------------------- -->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html>
	<head>
		<title><s:text name='transferProperty' /></title>
		<link href="<c:url value='/resources/global/css/bootstrap/bootstrap-datepicker.css' context='/egi'/>" rel="stylesheet" type="text/css" />
        <script src="<c:url value='/resources/global/js/bootstrap/bootstrap-datepicker.js' context='/egi'/>"></script>
		<script type="text/javascript">
		jQuery.noConflict();
		jQuery("#loadingMask").remove();
	function loadOnStartUp() {
		document.getElementById("saleDetail").className = "hiddentext";
		document.getElementById("saleDetail").readOnly = true;
		enableBlock();
		try { 
			jQuery(".datepicker").datepicker({
				format: "dd/mm/yyyy"
			}); 
		}catch(e){
			console.warn("No Date Picker "+ e);
		}

		jQuery('.datepicker').on('changeDate', function(ev){
			jQuery(this).datepicker('hide');
		});
		
		
	}
</script>
	</head>
	<body onload="loadOnStartUp();">
		<div class="formmainbox">
			<s:if test="%{hasErrors()}">
				<div class="errorstyle" id="property_error_area">
					<div class="errortext">
						<s:actionerror />
						<s:fielderror/>
					</div>
				</div>
			</s:if>
			<s:form action="save" name="transferform" theme="simple" enctype="multipart/form-data">
				<s:push value="model">
				<s:token/>
				<div class="headingbg">
						<s:text name="transferortitle" />
				</div>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="bluebox2" style="width:5%;">
							&nbsp;
						</td>
						<td class="bluebox" style="width:20%">
							<s:text name="prop.Id"></s:text> :
						</td>
						<td class="bluebox">
							<span class="bold"><s:property value="basicproperty.upicNo" default="N/A"/></span>
							<s:hidden name="assessmentNo" value="%{basicproperty.upicNo}"/>
						</td>
						<td class="bluebox">
							&nbsp;
						</td>
						<td style="width:25%;">&nbsp;</td>
					</tr>
					<tr>
						<td class="bluebox2">
							&nbsp;
						</td>
						<td class="bluebox">
							<s:text name="PropertyAddress"></s:text> :
						</td>
						<td class="bluebox">
							<span class="bold"><s:property value="basicproperty.address" /></span>
						</td>
						<td class="bluebox">
							<s:text name="Zone"></s:text> :
						</td>
						<td class="bluebox">
							<span class="bold"><s:property value="basicproperty.propertyID.zone.name" /></span>
						</td>
					</tr>
					
					<tr>
						<td class="greybox2">
							&nbsp;
						</td>
						<td class="greybox">
							<s:text name="Ward" /> :
						</td>
						<td class="greybox">
							<span class="bold"><s:property value="basicproperty.propertyID.ward.name" /></span>
						</td>
						<td class="greybox">
							<s:text name="block" /> :
						</td>
						<td class="greybox">
							<span class="bold"><s:property value="basicproperty.propertyID.area.name" /></span>
						</td>
					</tr>
					
					<tr>
						<td class="greybox2">
							&nbsp;
						</td>
						<td class="greybox">
							<s:text name="currentpropertytax" /> :
						</td>
						<td class="greybox">
							<span class="bold">Rs. <s:property value="currentPropertyTax" /> /-</span>
						</td>
					</tr>
					<tr>
							<td colspan="5">
								<div class="headingsmallbg">
									<span class="bold"><s:text name="ownerdetails.title"></s:text></span>
								</div>
							</td>
					</tr>
					<tr>
						<td colspan="5">
							<table class="tablebottom" id="" width="100%" border="0"
								cellpadding="0" cellspacing="0">
								<tbody>
									<tr>
									    <th class="bluebgheadtd"><s:text name="adharno"/></th>
									    <th class="bluebgheadtd"><s:text name="salutation"/></th>
									    <th class="bluebgheadtd"><s:text name="OwnerName"/></th>
									    <th class="bluebgheadtd"><s:text name="gender"/></th>
										<th class="bluebgheadtd"><s:text name="MobileNumber" /></th>
										<th class="bluebgheadtd"><s:text name="EmailAddress"/></th>
										<th class="bluebgheadtd"><s:text name="GuardianRelation"/></th>
										<th class="bluebgheadtd"><s:text name="Guardian"/></th>
									</tr>
									<s:iterator value="basicproperty.propertyOwnerInfo" status="status">
									<tr>
										<td class="blueborderfortd" align="center"><s:property value="owner.aadhaarNumber"/></td>
										<td class="blueborderfortd" align="center"><s:property value="owner.salutation" /></td>
										<td class="blueborderfortd" align="center"><s:property value="owner.name" /></td>
										<td class="blueborderfortd" align="center"><s:property value="owner.gender" /></td>
										<td class="blueborderfortd" align="center"><s:property value="owner.mobileNumber" /></td>
										<td class="blueborderfortd" align="center"><s:property value="owner.emailId" /></td>
										<td class="blueborderfortd" align="center"><s:property value="owner.guardianRelation" default="N/A"/></td>
										<td class="blueborderfortd" align="center"><s:property value="owner.guardian" default="N/A"/></td>
									</tr>
									</s:iterator>
								</tbody>
							  </table> 
						</td>
					</tr>
					<tr>
						<td colspan="5">
							<div class="headingsmallbg">
								<s:text name="transferDtls" />
							</div>
						</td>
					</tr>

					<tr>
						<td colspan="5">
							<div>
								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tablebottom" id="nameTable" >
								    <tr>
								    	<th class="bluebgheadtd"><s:text name="adharno"/><span class="mandatory1">*</span></th>
								    	<th class="bluebgheadtd"><s:text name="salutation"/><span class="mandatory1">*</span></th>
										<th class="bluebgheadtd"><s:text name="OwnerName"/><span class="mandatory1">*</span></th>
										<th class="bluebgheadtd"><s:text name="gender"/><span class="mandatory1">*</span></th>
										<th class="bluebgheadtd"><s:text name="MobileNumber" />(without +91)<span class="mandatory1">*</span></th>
										<th class="bluebgheadtd"><s:text name="EmailAddress"/><span class="mandatory1">*</span></th>
										<th class="bluebgheadtd"><s:text name="GuardianRelation"/></th>
										<th class="bluebgheadtd"><s:text name="Guardian"/></th>
										<th class="bluebgheadtd">Add/Delete</th>
									</tr>
									<s:if test="%{transfereeInfos.size == 0}">
								      <tr id="nameRow" >
								        <td class="blueborderfortd" align="center">
										   <s:textfield name="transfereeInfos[0].aadhaarNumber" size="12" maxlength="12" value=""></s:textfield>
										</td>
										<td class="blueborderfortd" align="center">
								            <s:select name="transfereeInfos[0].salutation" id="transfereeInfos[0].salutation" headerValue="Choose" 	headerKey="" list="#{'Mr':'Mr','Ms':'Ms','Mrs':'Mrs' }" value="%{transfereeInfos[0].salutation}"
				                             cssClass="selectwk"></s:select>
								        </td>
								        <td class="blueborderfortd" align="center">
								        	<s:textfield name="transfereeInfos[0].name" maxlength="100" size="20" id="ownerName"  value="" 
								        		onblur="trim(this,this.value);checkSpecialCharForName(this);"/>
								        </td>
								        <td class="blueborderfortd" align="center">
								        	<s:select name="transfereeInfos[0].gender" list="@org.egov.infra.persistence.entity.enums.Gender@values()"></s:select>
								        </td>
								        <td class="blueborderfortd" align="center">
								        	<s:textfield name="transfereeInfos[0].mobileNumber" maxlength="10" size="20" id="mobileNumber"  value="" 
								        		onblur="validNumber(this);checkZero(this,'Mobile Number');"/>
								        </td>
								        <td class="blueborderfortd" align="center">
								        	<s:textfield name="transfereeInfos[0].emailId" maxlength="64" size="20" id="emailId"  value="" 
								        		onblur="trim(this,this.value);validateEmail(this);"/>
								        	<!-- This hidden field can become dropdown later when transferee become non citizen -->
								        	<s:hidden name="transfereeInfos[0].type" value="CITIZEN" data-static="true"/>
								        </td>
								        <td class="blueborderfortd" align="center">
								        		 <s:select id="transfereeInfos[0].guardianRelation" name="transfereeInfos[0].guardianRelation" value="%{transfereeInfos[0].guardianRelation}"
				                                     headerValue="Choose" headerKey="" list="guardianRelationMap"/>
								        </td>
								         <td class="blueborderfortd" align="center">
								        	<s:textfield name="transfereeInfos[0].guardian" maxlength="100" size="20" 
								        		onblur="trim(this,this.value);checkSpecialCharForName(this);"/>
								        </td>
								        <td class="blueborderfortd">
								        	<img id="addOwnerBtn" name="addOwnerBtn" src="${pageContext.request.contextPath}/resources/image/addrow.gif" onclick="javascript:addOwner(); return false;" alt="Add" width="18" height="18" border="0" />
								      		<img id="removeOwnerBtn" name="removeOwnerBtn" src="${pageContext.request.contextPath}/resources/image/removerow.gif" onclick="javascript:deleteOwner(this); return false;" alt="Remove" width="18" height="18" border="0" />
								        </td>
								     </tr>
								     </s:if>
								     <s:else>
								     <s:iterator value="transfereeInfos" status="status" >
								      <tr id="nameRow" >
								        <td class="blueborderfortd" align="center">
										   <s:textfield name="transfereeInfos[%{#status.index}].aadhaarNumber" size="12" maxlength="12"></s:textfield>
										</td>
										<td class="blueborderfortd" align="center">
								        	<s:select name="transfereeInfos[%{#status.index}].salutation" id="transfereeInfos[%{#status.index}].salutation" headerValue="Choose" 	headerKey="" list="#{'Mr':'Mr','Ms':'Ms','Mrs':'Mrs' }"
				                                   cssClass="selectwk"></s:select>
								        </td>
								        <td class="blueborderfortd" align="center">
								        	<s:textfield name="transfereeInfos[%{#status.index}].name" maxlength="100" size="20"
								        		onblur="trim(this,this.value);checkSpecialCharForName(this);"/>
								        </td>
								        <td class="blueborderfortd" align="center">
								        	<s:select name="transfereeInfos[%{#status.index}].gender" list="@org.egov.infra.persistence.entity.enums.Gender@values()"></s:select>
								        </td>
								        <td class="blueborderfortd" align="center">
								        	<s:textfield name="transfereeInfos[%{#status.index}].mobileNumber" maxlength="10" size="20"
								        		onblur="validNumber(this);checkZero(this,'Mobile Number');"/>
								        </td>
								        <td class="blueborderfortd" align="center">
								        	<s:textfield name="transfereeInfos[%{#status.index}].emailId" maxlength="64" size="20" 
								        		onblur="trim(this,this.value);validateEmail(this);"/>
								        		<!-- This hidden field can become dropdown later when transferee become non citizen -->
								        	<s:hidden name="transfereeInfos[%{#status.index}].type" value="CITIZEN" data-static="true"/>
								        </td>
								        <td class="blueborderfortd" align="center">
								         <s:select id="transfereeInfos[%{#status.index}].guardianRelation" name="transfereeInfos[%{#status.index}].guardianRelation" value="%{transfereeInfos[%{#status.index}].guardianRelation}"
				                                     headerValue="Choose" headerKey="" list="guardianRelationMap"/>
								
								        </td>
								        <td class="blueborderfortd" align="center">
								        	<s:textfield name="transfereeInfos[%{#status.index}].guardian" maxlength="100" size="20"
								        		onblur="trim(this,this.value);checkSpecialCharForName(this);"/>
								        </td>
								        
								        <td class="blueborderfortd">
								        	<img id="addOwnerBtn" name="addOwnerBtn" src="${pageContext.request.contextPath}/resources/image/addrow.gif" onclick="javascript:addOwner(); return false;" alt="Add" width="18" height="18" border="0" />
								      		<img id="removeOwnerBtn" name="removeOwnerBtn" src="${pageContext.request.contextPath}/resources/image/removerow.gif" onclick="javascript:deleteOwner(this); return false;" alt="Remove" width="18" height="18" border="0" />
								        </td>
								     </tr>
								     </s:iterator>
								     </s:else>
								</table>
							</div>
							<br/>
						</td>
					</tr>

					<tr>
						<td class="greybox2">
							&nbsp;
						</td>
						<td class="greybox">
							<s:text name="transferreason"></s:text>
							<span class="mandatory1">*</span> :
						</td>
						<td class="greybox">
							<s:select name="mutationReason" id="transRsnId"
								list="dropdownData.MutationReason" listKey="id"
								listValue="mutationName" headerKey="-1"
								headerValue="%{getText('default.select')}"
								value="%{mutationReason.id}"
								onchange="enableSaleDtls(this);" />
						</td>
						<td class="greybox">
							<s:text name="saleDetls" />
							<span class="mandatory1">*</span> :
						</td>
						<td class="greybox">
							<s:textarea cols="30" rows="2" name="saleDetail" id="saleDetail"
								onchange="return validateMaxLength(this);"
								onblur="trim(this,this.value);"></s:textarea>
						</td>
					</tr>
					
					<tr>
						<td class="greybox2">
							&nbsp;
						</td>
						<td class="greybox">
							<s:text name="docNum" /><span class="mandatory1">*</span> :
						</td>
						<td class="greybox">
							<s:textfield name="deedNo" id="docNum"  maxlength="64"/>
						</td>
						<td class="greybox">
							<s:text name="docDate" /><span class="mandatory1">*</span> :
						</td>
						<td class="greybox">
							<s:date name="deedDate" var="docDate" format="dd/MM/yyyy" />
							<s:textfield name="deedDate" id="deedDate" maxlength="10"
								value="%{docDate}" autocomplete="off"
								onkeyup="DateFormat(this,this.value,event,false,'3')"
								onblur="validateDateFormat(this);"
								cssClass="datepicker" />
						</td>
					</tr>
					
				<%-- 	<tr>
						<td colspan="5">
							<div class="headingsmallbg">
								<s:text name="docsectiontitle" /> 
							</div>
						</td>
					</tr> --%>

                    <%@ include file="../common/DocumentUploadForm.jsp"%>
                     <%-- <tr>
						<td colspan="5">
						<table class="tablebottom" id="nameTable" width="100%" border="0" cellpadding="0" cellspacing="0">
							<tbody>
								<tr>
									<th class="bluebgheadtd"><s:text name="doctable.docenclosed" /></th>
									<th class="bluebgheadtd"><s:text name="doctable.doctype" /></th>
									<th class="bluebgheadtd"><s:text name="doctable.docdate" /></th>
									<th class="bluebgheadtd"><s:text name="doctable.docdetails" /></th>
									<th class="bluebgheadtd">Upload File</th>
								</tr>
								<s:iterator value="documentTypes" status="status" var="documentType">
								<tr>
									<td class="blueborderfortd" align="center">
									  <s:checkbox name="documents[%{#status.index}].enclosed" id="docDetail[%{#status.index}].enclosed"/>
									</td>
									<td class="blueborderfortd" style="text-align:left">
									  <s:property value="name"/><s:if test="mandatory"><span class="mandatory1">*</span></s:if>
									  <s:hidden name="documents[%{#status.index}].type.id" value="%{id}"></s:hidden>
									</td>
									<td class="blueborderfortd" align="center">
									  <s:textfield name="documents[%{#status.index}].docDate" id="docDetail[%{#status.index}].docDate" cssClass='datepicker' maxlength="10"/>
									</td>
									<td class="blueborderfortd" align="center">
										<s:textarea name="documents[%{#status.index}].description" id="docDetail[%{#status.index}].description" cols="40" rows="2"></s:textarea>
									</td>
									<td class="blueborderfortd" align="center">
										<s:file name="documents[%{#status.index}].uploads" cssClass="button"/> 
									</td>
								</tr>
								</s:iterator>
							</tbody>
						</table>
					</td>
				 </tr> --%>

				</table>
				<s:if test="%{propertyByEmployee == true}">
        		<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<%@ include file="../workflow/commonWorkflowMatrix.jsp"%>
					</tr>
					<tr>
						<%@ include file="../workflow/commonWorkflowMatrix-button.jsp"%>
					</tr>
				</table>
				</s:if>
				<s:else>
				<tr align="center">
					<%@ include file="../workflow/commonWorkflowMatrix-button.jsp"%>
				</tr>
				</s:else>
				</s:push>
			</s:form>
			<div align="left" class="mandatory1" style="font-size: 11px">
				* Mandatory Fields
			</div>
		</div>
		<script type="text/javascript">
		
		function enableSaleDtls(obj) {
			var selectedValue = obj.options[obj.selectedIndex].text;
			if(selectedValue=='<s:property value="%{@org.egov.ptis.constants.PropertyTaxConstants@MUTATIONRS_SALES_DEED}" />') {
				document.getElementById("saleDetail").readOnly=false;
				document.getElementById("saleDetail").className="";
			}
			else {
				document.getElementById("saleDetail").value="";
				document.getElementById("saleDetail").className="hiddentext";
				document.getElementById("saleDetail").readOnly=true;
			}
			
			if(selectedValue=='<s:property value="%{@org.egov.ptis.constants.PropertyTaxConstants@MUTATIONRS_OTHERS}" />') { 
				document.getElementById("mutationRsnRow").style.display="";
			}
			else {
				document.getElementById("mutationRsnRow").style.display="none";
			}
		}
		function confirmClose(){
	 		var result = confirm("Do you want to close the window?");
	 		if(result==true){
	 			window.close();
	 			return true;
	 		}else{
	 			return false;
	 		}
	 	}		
		function enableBlock(){
	   		var obj=document.getElementById("transRsnId");
	 		if(obj!=null || obj!="undefined"){
	  			var selectedValue = obj.options[obj.selectedIndex].text;
	 			if(selectedValue=='<s:property value="%{@org.egov.ptis.constants.PropertyTaxConstants@MUTATIONRS_SALES_DEED}" />') { 
					document.getElementById("saleDetail").readOnly=false;
					document.getElementById("saleDetail").className="";
				} else {
					document.getElementById("saleDetail").value="";
					document.getElementById("saleDetail").className="hiddentext";
					document.getElementById("saleDetail").readOnly=true;
				}
			}
		}
</script>
</body>
</html>