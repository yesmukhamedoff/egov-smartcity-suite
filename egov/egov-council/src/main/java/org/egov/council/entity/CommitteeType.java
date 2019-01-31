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

package org.egov.council.entity;

import org.egov.infra.persistence.entity.AbstractAuditable;
import org.egov.infra.persistence.validator.annotation.Unique;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static org.egov.infra.validation.constants.ValidationErrorCode.INVALID_ALPHANUMERIC_WITH_SPACE;
import static org.egov.infra.validation.constants.ValidationRegex.ALPHANUMERIC;

import java.util.ArrayList;
import java.util.List;

@Entity
@Unique(id = "id", tableName = "egcncl_committeeType", fields = { "code", "name" }, columnName = { "code", "name" }, enableDfltMsg = true)
@Table(name = "egcncl_committeeType")
@SequenceGenerator(name = CommitteeType.SEQ_COMMITTEETYPE, sequenceName = CommitteeType.SEQ_COMMITTEETYPE, allocationSize = 1)
public class CommitteeType extends AbstractAuditable {

    private static final long serialVersionUID = 5920779296207990727L;

    public static final String SEQ_COMMITTEETYPE = "seq_egcncl_committeeType";

    @Id
    @GeneratedValue(generator = SEQ_COMMITTEETYPE, strategy = GenerationType.SEQUENCE)
    Long id;

    @NotNull
    @SafeHtml
    @Length(min = 2, max = 100)
    @Pattern(regexp = ALPHANUMERIC, message=INVALID_ALPHANUMERIC_WITH_SPACE)
    private String name;

    @NotNull
    @SafeHtml
    @Length(max = 20)
    @Column(name = "code", updatable = false)
    private String code;

    @NotNull
    private Boolean isActive;
    
    @OneToMany(mappedBy = "committeeType", cascade = CascadeType.ALL)
    private List<CommitteeMembers> commiteemembers= new ArrayList<CommitteeMembers>();
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<CommitteeMembers> getCommiteemembers() {
        return commiteemembers;
    }

    public void setCommiteemembers(List<CommitteeMembers> commiteemembers) {
        this.commiteemembers = commiteemembers;
    }
    
    public void deleteCommiteemembers(CommitteeMembers commiteemembers) {
        if(this.commiteemembers != null ){
            this.commiteemembers.remove(commiteemembers);
        }
    }
    
    public void addCommiteemembers(CommitteeMembers commiteemembers) {
        if(this.commiteemembers != null ){
            this.commiteemembers.add(commiteemembers);
        }
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CommitteeType other = (CommitteeType) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
    
    
}
