
package de.css_lims.dkmsroboterrueckmeldung;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="requestTyp" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="hinweisText" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="roboterId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="programmId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="userBedienerId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="userKontrolleurId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="zeitstempel" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="rackId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="versuchschrittId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dynamischerTeil"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="dynamischerTyp" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dynamischesObjekt" maxOccurs="unbounded"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                                       &lt;element name="wertObjekt" maxOccurs="unbounded"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="inhalt" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",
         propOrder =
         { "requestTyp", "requestId", "hinweisText", "roboterId", "programmId", "userBedienerId", "userKontrolleurId",
           "zeitstempel", "rackId", "versuchschrittId", "dynamischerTeil"
    })
@XmlRootElement(name = "RueckmeldungRequest")
public class RueckmeldungRequest {

    @XmlElement(required = true)
    protected BigInteger requestTyp;
    @XmlElement(required = true)
    protected String requestId;
    @XmlElement(required = true, nillable = true)
    protected String hinweisText;
    @XmlElement(required = true)
    protected String roboterId;
    @XmlElement(required = true)
    protected String programmId;
    @XmlElement(required = true)
    protected String userBedienerId;
    @XmlElement(required = true, nillable = true)
    protected String userKontrolleurId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar zeitstempel;
    @XmlElement(required = true)
    protected String rackId;
    @XmlElement(required = true, nillable = true)
    protected String versuchschrittId;
    @XmlElement(required = true)
    protected RueckmeldungRequest.DynamischerTeil dynamischerTeil;

    /**
     * Gets the value of the requestTyp property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    public BigInteger getRequestTyp() {
        return requestTyp;
    }

    /**
     * Sets the value of the requestTyp property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setRequestTyp(BigInteger value) {
        this.requestTyp = value;
    }

    /**
     * Gets the value of the requestId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRequestId(String value) {
        this.requestId = value;
    }

    /**
     * Gets the value of the hinweisText property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getHinweisText() {
        return hinweisText;
    }

    /**
     * Sets the value of the hinweisText property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setHinweisText(String value) {
        this.hinweisText = value;
    }

    /**
     * Gets the value of the roboterId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRoboterId() {
        return roboterId;
    }

    /**
     * Sets the value of the roboterId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRoboterId(String value) {
        this.roboterId = value;
    }

    /**
     * Gets the value of the programmId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getProgrammId() {
        return programmId;
    }

    /**
     * Sets the value of the programmId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setProgrammId(String value) {
        this.programmId = value;
    }

    /**
     * Gets the value of the userBedienerId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUserBedienerId() {
        return userBedienerId;
    }

    /**
     * Sets the value of the userBedienerId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUserBedienerId(String value) {
        this.userBedienerId = value;
    }

    /**
     * Gets the value of the userKontrolleurId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUserKontrolleurId() {
        return userKontrolleurId;
    }

    /**
     * Sets the value of the userKontrolleurId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUserKontrolleurId(String value) {
        this.userKontrolleurId = value;
    }

    /**
     * Gets the value of the zeitstempel property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getZeitstempel() {
        return zeitstempel;
    }

    /**
     * Sets the value of the zeitstempel property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setZeitstempel(XMLGregorianCalendar value) {
        this.zeitstempel = value;
    }

    /**
     * Gets the value of the rackId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRackId() {
        return rackId;
    }

    /**
     * Sets the value of the rackId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRackId(String value) {
        this.rackId = value;
    }

    /**
     * Gets the value of the versuchschrittId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getVersuchschrittId() {
        return versuchschrittId;
    }

    /**
     * Sets the value of the versuchschrittId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setVersuchschrittId(String value) {
        this.versuchschrittId = value;
    }

    /**
     * Gets the value of the dynamischerTeil property.
     *
     * @return
     *     possible object is
     *     {@link RueckmeldungRequest.DynamischerTeil }
     *
     */
    public RueckmeldungRequest.DynamischerTeil getDynamischerTeil() {
        return dynamischerTeil;
    }

    /**
     * Sets the value of the dynamischerTeil property.
     *
     * @param value
     *     allowed object is
     *     {@link RueckmeldungRequest.DynamischerTeil }
     *
     */
    public void setDynamischerTeil(RueckmeldungRequest.DynamischerTeil value) {
        this.dynamischerTeil = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="dynamischerTyp" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dynamischesObjekt" maxOccurs="unbounded"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *                             &lt;element name="wertObjekt" maxOccurs="unbounded"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="inhalt" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "dynamischerTyp" })
    public static class DynamischerTeil {

        @XmlElement(required = true)
        protected List<RueckmeldungRequest.DynamischerTeil.DynamischerTyp> dynamischerTyp;

        /**
         * Gets the value of the dynamischerTyp property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dynamischerTyp property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDynamischerTyp().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RueckmeldungRequest.DynamischerTeil.DynamischerTyp }
         *
         *
         */
        public List<RueckmeldungRequest.DynamischerTeil.DynamischerTyp> getDynamischerTyp() {
            if (dynamischerTyp == null) {
                dynamischerTyp = new ArrayList<RueckmeldungRequest.DynamischerTeil.DynamischerTyp>();
            }
            return this.dynamischerTyp;
        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dynamischesObjekt" maxOccurs="unbounded"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
         *                   &lt;element name="wertObjekt" maxOccurs="unbounded"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="inhalt" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "name", "dynamischesObjekt" })
        public static class DynamischerTyp {

            @XmlElement(required = true)
            protected String name;
            @XmlElement(required = true)
            protected List<RueckmeldungRequest.DynamischerTeil.DynamischerTyp.DynamischesObjekt> dynamischesObjekt;

            /**
             * Gets the value of the name property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Gets the value of the dynamischesObjekt property.
             *
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the dynamischesObjekt property.
             *
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getDynamischesObjekt().add(newItem);
             * </pre>
             *
             *
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RueckmeldungRequest.DynamischerTeil.DynamischerTyp.DynamischesObjekt }
             *
             *
             */
            public List<RueckmeldungRequest.DynamischerTeil.DynamischerTyp.DynamischesObjekt> getDynamischesObjekt() {
                if (dynamischesObjekt == null) {
                    dynamischesObjekt =
                        new ArrayList<RueckmeldungRequest.DynamischerTeil.DynamischerTyp.DynamischesObjekt>();
                }
                return this.dynamischesObjekt;
            }


            /**
             * <p>Java class for anonymous complex type.
             *
             * <p>The following schema fragment specifies the expected content contained within this class.
             *
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence&gt;
             *         &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
             *         &lt;element name="wertObjekt" maxOccurs="unbounded"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="inhalt" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *       &lt;/sequence&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { "position", "wertObjekt" })
            public static class DynamischesObjekt {

                @XmlElement(required = true)
                protected BigInteger position;
                @XmlElement(required = true)
                protected List<RueckmeldungRequest.DynamischerTeil.DynamischerTyp.DynamischesObjekt.WertObjekt> wertObjekt;

                /**
                 * Gets the value of the position property.
                 *
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *
                 */
                public BigInteger getPosition() {
                    return position;
                }

                /**
                 * Sets the value of the position property.
                 *
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *
                 */
                public void setPosition(BigInteger value) {
                    this.position = value;
                }

                /**
                 * Gets the value of the wertObjekt property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the wertObjekt property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getWertObjekt().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link RueckmeldungRequest.DynamischerTeil.DynamischerTyp.DynamischesObjekt.WertObjekt }
                 *
                 *
                 */
                public List<RueckmeldungRequest.DynamischerTeil.DynamischerTyp.DynamischesObjekt.WertObjekt> getWertObjekt() {
                    if (wertObjekt == null) {
                        wertObjekt =
                            new ArrayList<RueckmeldungRequest.DynamischerTeil.DynamischerTyp.DynamischesObjekt.WertObjekt>();
                    }
                    return this.wertObjekt;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 *
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 *
                 * <pre>
                 * &lt;complexType&gt;
                 *   &lt;complexContent&gt;
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *       &lt;sequence&gt;
                 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="inhalt" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *       &lt;/sequence&gt;
                 *     &lt;/restriction&gt;
                 *   &lt;/complexContent&gt;
                 * &lt;/complexType&gt;
                 * </pre>
                 *
                 *
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = { "name", "inhalt" })
                public static class WertObjekt {

                    @XmlElement(required = true)
                    protected String name;
                    @XmlElement(required = true)
                    protected String inhalt;

                    /**
                     * Gets the value of the name property.
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getName() {
                        return name;
                    }

                    /**
                     * Sets the value of the name property.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setName(String value) {
                        this.name = value;
                    }

                    /**
                     * Gets the value of the inhalt property.
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getInhalt() {
                        return inhalt;
                    }

                    /**
                     * Sets the value of the inhalt property.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setInhalt(String value) {
                        this.inhalt = value;
                    }

                }

            }

        }

    }

    
}
