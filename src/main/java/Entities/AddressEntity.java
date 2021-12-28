package Entities;

import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "address", schema = "sakila", catalog = "")
public class AddressEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "address_id")
    private Short addressId;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "address2")
    private String address2;
    @Basic
    @Column(name = "district")
    private String district;
    @Basic
    @Column(name = "city_id")
    private Short cityId;
    @Basic
    @Column(name = "postal_code")
    private String postalCode;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Column(name = "location")
    private Point location;
    @Basic
    @Column(name = "last_update")
    private Timestamp lastUpdate;



    public Short getAddressId() {
        return addressId;
    }

    public void setAddressId(Short addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Short getCityId() {
        return cityId;
    }

    public void setCityId(Short cityId) {
        this.cityId = cityId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public AddressEntity() {
    }

//    public AddressEntity(String address, String district, Short cityID, String phone, Double longitude, Double latitude, Timestamp lastUpdate) {
//        GeometryFactory geometryFactory = new GeometryFactory();
//        this.address = address;
//        this.district = district;
//        this.cityId = cityID;
//        this.phone = phone;
//        this.location = geometryFactory.createPoint(new Coordinate(10.0, 5.0));
//        this.lastUpdate = lastUpdate;
//    }

    public AddressEntity(String address, String address2, String district, Short cityId, String postalCode, String phone, Double longitude, Double latitude, Timestamp lastUpdate) {
        GeometryFactory geometryFactory = new GeometryFactory();
        this.address = address;
        this.address2 = address2;
        this.district = district;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.phone = phone;
        this.location = geometryFactory.createPoint(new Coordinate(10, 5));
        this.lastUpdate = lastUpdate;
    }

}
