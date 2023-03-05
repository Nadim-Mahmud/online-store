package net.therap.onlinestore.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @author nadimmahmud
 * @since 3/4/23
 */
@MappedSuperclass
public class Persistent {

    @Enumerated(EnumType.STRING)
    @Column(name = "access_status")
    private AccessStatus accessStatus;

    @Version
    @Column(name = "version", nullable = false)
    protected int version;

    @CreationTimestamp
    @Column(name = "created_at")
    protected Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    protected Date updatedAt;

    public Persistent() {
        this.accessStatus = AccessStatus.ACTIVE;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public AccessStatus getAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(AccessStatus accessStatus) {
        this.accessStatus = accessStatus;
    }

}
