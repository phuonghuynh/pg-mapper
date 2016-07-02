package test.net.pg.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import net.pg.mapper.core.ann.DomainMapping;

import org.apache.sling.commons.json.JSONObject;

@XmlAccessorType(XmlAccessType.FIELD)
public class HatsPropertiesInput implements Serializable {

   private static final long serialVersionUID = 1L;

   @XmlTransient
   private JSONObject messageJSON;

   @XmlElement
   private String password;

   @XmlElement
   private String userID;

   @XmlElement
   @DomainMapping(xpath = "/accountNo")
   private String accountNo;

   @XmlElement
   @DomainMapping(name = "tdNo", xpath = "/tdNo", directions={"rbk_host_inquiry"})
   private String tdNO;

   @XmlElement
   private String batchDesc;

   @XmlElement
   private String depID;

   @XmlElement(name = "HPubLinkKey")
   private String HPubLinkKey;

   @XmlElement
   @DomainMapping(name = "holdcode", xpath = "/holdcode")
   private String holdCode;

   @XmlElement
   private String holdCodeNarrative;

   @XmlElement
   private String holdCodeNarrativeRemarks;

   @XmlElement(name = "IBatchID")
   private String iBatchID;

   @XmlElement
   private String batchID;

   @XmlElement
   private String requestorName;

   @XmlElement
   private String referenceNo;

   @XmlElement
   private String expiryDate;

   @XmlElement
   @DomainMapping(xpath = "/tdNo", directions={"gtd_host_inquiry"})
   private String tdNo;

   @XmlElement
   private String transactionType;

   @XmlElement
   private String amount;

   @XmlElement
   private String remark;

   @XmlElement
   private String deptID;

   // NOTE: dont use this constructor, use the second one instead
   public HatsPropertiesInput() {
   }

   public HatsPropertiesInput(JSONObject messageJSON) {
      setMessageJSON(messageJSON);
   }

   public String getDeptID() {
      return deptID;
   }

   public void setDeptID(String deptID) {
      this.deptID = deptID;
   }

   public String getAmount() {
      return amount;
   }

   public void setAmount(String amount) {
      this.amount = amount;
   }

   public String getRemark() {
      return remark;
   }

   public void setRemark(String remark) {
      this.remark = remark;
   }

   public String getTransactionType() {
      return transactionType;
   }

   public void setTransactionType(String transactionType) {
      this.transactionType = transactionType;
   }

   public String getExpiryDate() {
      return expiryDate;
   }

   public void setExpiryDate(String expiryDate) {
      this.expiryDate = expiryDate;
   }

   public String getTdNo() {
      return tdNo;
   }

   public void setTdNo(String tdNo) {
      this.tdNo = tdNo;
   }

   public String getTdNO() {
      return tdNO;
   }

   public void setTdNO(String tdNO) {
      this.tdNO = tdNO;
   }

   public String getReferenceNo() {
      return referenceNo;
   }

   public void setReferenceNo(String referenceNo) {
      this.referenceNo = referenceNo;
   }

   public String getAccountNo() {
      return accountNo;
   }

   public void setAccountNo(String accountNo) {
      this.accountNo = accountNo;
   }

   public String getBatchDesc() {
      return batchDesc;
   }

   public void setBatchDesc(String batchDesc) {
      this.batchDesc = batchDesc;
   }

   public String getDepID() {
      return depID;
   }

   public void setDepID(String depID) {
      this.depID = depID;
   }

   public String getHPubLinkKey() {
      return HPubLinkKey;
   }

   public void setHPubLinkKey(String hPubLinkKey) {
      HPubLinkKey = hPubLinkKey;
   }

   public String getHoldCode() {
      return holdCode;
   }

   public void setHoldCode(String holdCode) {
      this.holdCode = holdCode;
   }

   public String getHoldCodeNarrative() {
      return holdCodeNarrative;
   }

   public void setHoldCodeNarrative(String holdCodeNarrative) {
      this.holdCodeNarrative = holdCodeNarrative;
   }

   public String getHoldCodeNarrativeRemarks() {
      return holdCodeNarrativeRemarks;
   }

   public void setHoldCodeNarrativeRemarks(String holdCodeNarrativeRemarks) {
      this.holdCodeNarrativeRemarks = holdCodeNarrativeRemarks;
   }

   public String getIBatchID() {
      return iBatchID;
   }

   public void setIBatchID(String iBatchID) {
      this.iBatchID = iBatchID;
   }

   public String getBatchID() {
      return batchID;
   }

   public void setBatchID(String batchID) {
      this.batchID = batchID;
   }

   public String getRequestorName() {
      return requestorName;
   }

   public void setRequestorName(String requestorName) {
      this.requestorName = requestorName;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getUserID() {
      return userID;
   }

   public void setUserID(String userID) {
      this.userID = userID;
   }

   public String getiBatchID() {
      return iBatchID;
   }

   public void setiBatchID(String iBatchID) {
      this.iBatchID = iBatchID;
   }

   public JSONObject getMessageJSON() {
      return messageJSON;
   }

   public void setMessageJSON(JSONObject messageJSON) {
      this.messageJSON = messageJSON;
   }

}
