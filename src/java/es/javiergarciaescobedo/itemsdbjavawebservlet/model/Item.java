package es.javiergarciaescobedo.itemsdbjavawebservlet.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Javier García Escobedo <javiergarciaescobedo.es>
 */
@Entity
@Table(name = "item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
    @NamedQuery(name = "Item.findById", query = "SELECT i FROM Item i WHERE i.id = :id"),
    @NamedQuery(name = "Item.findByAstring", query = "SELECT i FROM Item i WHERE i.astring = :astring"),
    @NamedQuery(name = "Item.findByAnumber", query = "SELECT i FROM Item i WHERE i.anumber = :anumber"),
    @NamedQuery(name = "Item.findByAdate", query = "SELECT i FROM Item i WHERE i.adate = :adate"),
    @NamedQuery(name = "Item.findByAtime", query = "SELECT i FROM Item i WHERE i.atime = :atime"),
    @NamedQuery(name = "Item.findByAboolean", query = "SELECT i FROM Item i WHERE i.aboolean = :aboolean"),
    @NamedQuery(name = "Item.findByAprice", query = "SELECT i FROM Item i WHERE i.aprice = :aprice"),
    @NamedQuery(name = "Item.findByAdouble", query = "SELECT i FROM Item i WHERE i.adouble = :adouble")})
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "astring")
    private String astring;
    @Column(name = "anumber")
    private Integer anumber;
    @Column(name = "adate")
    @Temporal(TemporalType.DATE)
    private Date adate;
    @Column(name = "atime")
    @Temporal(TemporalType.TIME)
    private Date atime;
    @Column(name = "aboolean")
    private Boolean aboolean;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "aprice")
    private BigDecimal aprice;
    @Column(name = "adouble")
    private Double adouble;
    @JoinColumn(name = "category", referencedColumnName = "id")
    @ManyToOne
    private Category category;

    public Item() {
    }

    public Item(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAstring() {
        return astring;
    }

    public void setAstring(String astring) {
        this.astring = astring;
    }

    public Integer getAnumber() {
        return anumber;
    }

    public void setAnumber(Integer anumber) {
        this.anumber = anumber;
    }

    public Date getAdate() {
        return adate;
    }

    public void setAdate(Date adate) {
        this.adate = adate;
    }

    public Date getAtime() {
        return atime;
    }

    public void setAtime(Date atime) {
        this.atime = atime;
    }

    public Boolean getAboolean() {
        return aboolean;
    }

    public void setAboolean(Boolean aboolean) {
        this.aboolean = aboolean;
    }

    public BigDecimal getAprice() {
        return aprice;
    }

    public void setAprice(BigDecimal aprice) {
        this.aprice = aprice;
    }

    public Double getAdouble() {
        return adouble;
    }

    public void setAdouble(Double adouble) {
        this.adouble = adouble;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.javiergarciaescobedo.itemsdbjavawebservlet.model.Item[ id=" + id + " ]";
    }
    
}
