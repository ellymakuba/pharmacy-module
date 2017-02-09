/**
 *
 */
package org.openmrs.module.pharmacy.dao.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.openmrs.Drug;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.module.pharmacy.dao.PharmacyDAO;
import org.openmrs.module.pharmacy.model.*;

import java.util.Date;
import java.util.List;

/**
 * @author Ampath Developers
 */
public class HibernatePharmacyDAO implements PharmacyDAO {

    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(HibernatePharmacyDAO.class);
    LocationSetter loc= new LocationSetter();

    private SessionFactory sessionFactory;

    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#saveIndicators(org.openmrs.module.pharmacy.model.Indicators)
     */

    public Indicators saveIndicators(Indicators indicators) {
        sessionFactory.getCurrentSession().saveOrUpdate(indicators);

        return indicators;
    }
    public DrugExtra saveDrugExtraObject(DrugExtra drugExtra) {
        sessionFactory.getCurrentSession().saveOrUpdate(drugExtra);
        return drugExtra;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getIndicators()
     */

    @SuppressWarnings("unchecked")
    public List<Indicators> getIndicators() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Indicators.class)
                .add(Expression.eq("voided", false));
        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getIndicatorsByUuid(String)
     */

    public Indicators getIndicatorsByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Indicators.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<Indicators> indicators = criteria.list();
        if (null == indicators || indicators.isEmpty()) {
            return null;
        }
        return indicators.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getIndicatorsListByUuid(String)
     */

    @SuppressWarnings("unchecked")
    public List<Indicators> getIndicatorsListByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Indicators.class)
                .add(Expression.eq("uuid", uuid)).add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyEncounter(org.openmrs.module.pharmacy.model.PharmacyEncounter)
     */

    public PharmacyEncounter savePharmacyEncounter(PharmacyEncounter pharmacyEncounter) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyEncounter);

        return pharmacyEncounter;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyEncounter()
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyEncounter> getPharmacyEncounter() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounter.class)
                .add(Expression.eq("voided", false))
                .add(Expression.eq("display", 0))
                .addOrder(Order.desc("dateCreated"));
        return criteria.list();
    }
    public List<PharmacyEncounter> getPharmacyEncounterListByLocationUUID(String locationUUID) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounter.class)
                .createAlias("location","loc")
                .add(Restrictions.eq("loc.uuid",locationUUID))
                .add(Restrictions.eq("display",0))
                .addOrder(Order.desc("dateCreated"));
        return criteria.list();
    }
    public List<PharmacyEncounter> getUnclearedPharmacyEncountersListByPersonID(Integer personID) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounter.class)
                .createAlias("person", "p")
                .add(Restrictions.eq("p.personId", personID))
                .add(Restrictions.eq("display", 0))
                .addOrder(Order.desc("dateCreated"));
        return criteria.list();
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyEncounterByUuid(String)
     */

    public PharmacyEncounter getPharmacyEncounterByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounter.class)
                .add(Expression.eq("uuid", uuid));
        PharmacyEncounter pharmacyEncounter=(PharmacyEncounter)criteria.uniqueResult();
        return pharmacyEncounter;
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyEncounterListByPatientId(Person)
     */
    @SuppressWarnings("unchecked")
    public List<PharmacyEncounter> getPharmacyEncounterListByPatientId(Person id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounter.class)
                .add(Expression.eq("person", id))
                .add(Expression.eq("voided", false));
        return criteria.list();
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyEncounterListByUuid(String)
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyEncounter> getPharmacyEncounterListByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounter.class)
                .add(Expression.eq("uuid", uuid)).add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyEncounterType(org.openmrs.module.pharmacy.model.PharmacyEncounterType)
     */

    public PharmacyEncounterType savePharmacyEncounterType(PharmacyEncounterType pharmacyEncounterType) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyEncounterType);

        return pharmacyEncounterType;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyEncounterType()
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyEncounterType> getPharmacyEncounterType() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounterType.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyEncounterTypeByUuid(String)
     */

    public PharmacyEncounterType getPharmacyEncounterTypeByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounterType.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyEncounterType> pharmacyEncounterType = criteria.list();
        if (null == pharmacyEncounterType || pharmacyEncounterType.isEmpty()) {
            return null;
        }
        return pharmacyEncounterType.get(0);
    }


    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyEncounterTypeByName(String)
     */

    public PharmacyEncounterType getPharmacyEncounterTypeByName(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounterType.class)
                .add(Expression.eq("name", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyEncounterType> pharmacyEncounterType = criteria.list();
        if (null == pharmacyEncounterType || pharmacyEncounterType.isEmpty()) {
            return null;
        }
        return pharmacyEncounterType.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyEncounterTypeListByUuid(String)
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyEncounterType> getPharmacyEncounterTypeListByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounterType.class)
                .add(Expression.eq("uuid", uuid));

        return criteria.list();
    }



    public PharmacyStore savePharmacyInventoryItem(PharmacyStore pharmacyStore) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyStore);

        return pharmacyStore;
    }




    public boolean savePharmacyInventory(List<PharmacyStore> pharmacyStore) {
        for (PharmacyStore pharmacy : pharmacyStore) {
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacy);
        }
        return true;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyInventory()
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyStore> getPharmacyInventory() {
        try{

        }catch (Exception e){

        }
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Expression.eq("voided", false))
                .createAlias("drugs","d")
                .addOrder(Order.asc("d.name"));
        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyInventoryByUuid(String)
     */

    public PharmacyStore getPharmacyInventoryByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Expression.eq("uuid", uuid));
        PharmacyStore pharmacyStore = (PharmacyStore)criteria.uniqueResult();
        return pharmacyStore;
    }
    public List<Drug> getPharmacyInventoryByNameAndLocation(String name,PharmacyLocations location) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .createAlias("drugs","d")
                .add(Restrictions.like("d.name",name+"%"))
                .add(Restrictions.eq("location", location))
                .setProjection(Projections.distinct(Projections.property("drugs")));
        return criteria.list();
    }
    public List<PharmacyStore> getPharmacyInventoryByLocation(PharmacyLocations location) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class).add(Expression.eq("location", location));
        return criteria.list();
    }
    public List<PharmacyStore> getPharmacyStoreByNameAndLocation(String name,PharmacyLocations location) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .createAlias("drugs","d")
                .add(Restrictions.like("d.name",name+"%"))
                .add(Restrictions.eq("location", location));
        return criteria.list();
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyInventoryByCategory(PharmacyCategory)
     *
     * */

    public  List<PharmacyStore> getPharmacyInventoryByCategory(PharmacyCategory uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Expression.eq("category", uuid));


        return criteria.list();
    }


    public PharmacyStore getPharmacyInventoryByDrugUuid(String uuid,PharmacyLocations location) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .createAlias("drugs", "d")
                .add(Restrictions.like("d.uuid", uuid))
                .add(Restrictions.eq("voided", false))
                .add(Restrictions.eq("location", location));

        @SuppressWarnings("unchecked")
        List<PharmacyStore> pharmacyStore = criteria.list();
        if (null == pharmacyStore || pharmacyStore.isEmpty()) {
            return null;
        }
        return pharmacyStore.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyInventoryListByUuid(String)
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyStore> getPharmacyInventoryListByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Expression.eq("uuid", uuid));

        return criteria.list();
    }



    public DrugTransactions saveDrugTransactions(DrugTransactions drugTransactions) {
        sessionFactory.getCurrentSession().saveOrUpdate(drugTransactions);

        return drugTransactions;
    }


    public boolean saveDrugTransactions(List<DrugTransactions> drugTransactions) {

        for (DrugTransactions pharmacyStore : drugTransactions) {
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacyStore);
        }
        return false;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#--..00000entory()
     */

    @SuppressWarnings("unchecked")
    public List<DrugTransactions> getDrugTransactions() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugTransactions.class)
                .add(Expression.eq("voided", false))
                .addOrder(Order.desc("dateCreated"));
        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugTransactionsByUuid(String)
     */

    public DrugTransactions getDrugTransactionsByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugTransactions.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<DrugTransactions> drugTransactions = criteria.list();
        if (null == drugTransactions || drugTransactions.isEmpty()) {
            return null;
        }
        return drugTransactions.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugTransactionsListByUuid(String)
     */

    @SuppressWarnings("unchecked")
    public List<DrugTransactions> getDrugTransactionsListByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugTransactions.class)
                .add(Expression.eq("uuid", uuid));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyExtra(List)
     */

    public boolean savePharmacyExtra(List<PharmacyExtra> pharmacyExtra) {
        for (PharmacyExtra pharmacyOs : pharmacyExtra) {
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacyOs);
        }
        return true;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyExtra()
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyExtra> getPharmacyExtra() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyExtra.class);

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyExtraByUuid(String)
     */

    public PharmacyExtra getPharmacyExtraByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyExtra.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyExtra> pharmacyOs = criteria.list();
        if (null == pharmacyOs || pharmacyOs.isEmpty()) {
            return null;
        }
        return pharmacyOs.get(0);
    }


    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyObs(List)
     */

    public boolean savePharmacyObs(List<PharmacyObs> pharmacyObs) {
        for (PharmacyObs pharmacyOs : pharmacyObs) {
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacyOs);
        }
        return true;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyObs()
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyObs> getPharmacyObs() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyObs.class);

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyObsByUuid(String)
     */

    public PharmacyObs getPharmacyObsByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyObs.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyObs> pharmacyObs = criteria.list();
        if (null == pharmacyObs || pharmacyObs.isEmpty()) {
            return null;
        }
        return pharmacyObs.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyObsByUuid(String)
     */

    public List<PharmacyObs> getPharmacyObsByPharmacyOrder(PharmacyOrders uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyObs.class)
                .add(Expression.eq("pharmacyOrder", uuid));

        return criteria.list();
    }


    @SuppressWarnings("unchecked")
    public List<PharmacyObs> getPharmacyObsByEncounterId(PharmacyEncounter uuid)
    {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyObs.class)
                .add(Expression.eq("pharmacyEncounter", uuid));


        return criteria.list();
    }
    public PharmacyObs getPharmacyObservationByPharmacyOrder(PharmacyOrders uuid)
    {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyObs.class)
                .add(Expression.eq("pharmacyOrder", uuid));
        PharmacyObs pharmacyObs=(PharmacyObs)criteria.uniqueResult();
        return pharmacyObs;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyOrders()
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyOrders> getPharmacyOrders() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyOrders.class)
                .add(Expression.eq("voided", false));

        return criteria.list();

    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyOrdersByUuid(String)
     */

    public PharmacyOrders getPharmacyOrdersByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyOrders.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyOrders> pharmacyOrders = criteria.list();
        if (null == pharmacyOrders || pharmacyOrders.isEmpty()) {
            return null;
        }
        return pharmacyOrders.get(0);
    }



    public  List<PharmacyOrders> getPharmacyOrdersByEncounterId(PharmacyEncounter uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyOrders.class)
                .add(Expression.eq("pharmacyEncounter", uuid));


        return criteria.list();
    }

    public PharmacyOrders getPharmacyOrderByEncounter(PharmacyEncounter uuid){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyOrders.class)
                .add(Expression.eq("pharmacyEncounter", uuid));
        PharmacyOrders pharmacyOrder= (PharmacyOrders)criteria.uniqueResult();
        return pharmacyOrder;
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#saveDrugDispenseSettings(org.openmrs.module.pharmacy.model.DrugDispenseSettings)
     */

    public DrugDispenseSettings saveDrugDispenseSettings(DrugDispenseSettings drugDispenseSettings) {
        sessionFactory.getCurrentSession().saveOrUpdate(drugDispenseSettings);

        return drugDispenseSettings;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugDispenseSettings()
     */

    public List<DrugDispenseSettings> getDrugDispenseSettings() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugDispenseSettings.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugDispenseSettingsByUuid(String)
     */

    public DrugDispenseSettings getDrugDispenseSettingsByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugDispenseSettings.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<DrugDispenseSettings> drugDispenseSettings = criteria.list();
        if (null == drugDispenseSettings || drugDispenseSettings.isEmpty()) {
            return null;
        }
        return drugDispenseSettings.get(0);
    }

    /* @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugDispenseSettingsByUuid(java.lang.String)
    */

    public DrugDispenseSettings getDrugDispenseSettingsByLocation(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugDispenseSettings.class)
                .createAlias("location","loc")
                .add(Restrictions.like("loc.name",name));
        @SuppressWarnings("unchecked")
        List<DrugDispenseSettings> drugDispenseSettings = criteria.list();
        if (null == drugDispenseSettings || drugDispenseSettings.isEmpty()) {
            return null;
        }
        return drugDispenseSettings.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugDispenseSettingsByBatch(int)
     */

    public DrugDispenseSettings getDrugDispenseSettingsByBatch(int name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugDispenseSettings.class)
                .add(Expression.eq("batchId", name));

        @SuppressWarnings("unchecked")
        List<DrugDispenseSettings> drugDispenseSettings = criteria.list();
        if (null == drugDispenseSettings || drugDispenseSettings.isEmpty()) {
            return null;
        }
        return drugDispenseSettings.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugDispenseSettingsByDrugId(Drug)
     */

    public DrugDispenseSettings getDrugDispenseSettingsByDrugId(Drug id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugDispenseSettings.class)
                .add(Expression.eq("drugId", id));

        @SuppressWarnings("unchecked")
        List<DrugDispenseSettings> drugDispenseSettings = criteria.list();
        if (null == drugDispenseSettings || drugDispenseSettings.isEmpty()) {
            return null;
        }
        return drugDispenseSettings.get(0);
    }
    public DrugDispenseSettings getDrugDispenseSettingsByDrugIdAndLocation(Integer drugID,String locationUUID) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugDispenseSettings.class)
                .createAlias("location", "loc")
                .add(Restrictions.like("loc.uuid",locationUUID))
                .createAlias("drugId","d")
                .add(Restrictions.eq("d.drugId", drugID));
        criteria.setMaxResults(1);
        DrugDispenseSettings drugDispenseSettings = (DrugDispenseSettings)criteria.uniqueResult();
        return drugDispenseSettings;
    }


    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacySupplier(org.openmrs.module.pharmacy.model.PharmacySupplier)
     */

    public PharmacySupplier savePharmacySupplier(PharmacySupplier pharmacySupplier) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacySupplier);

        return pharmacySupplier;
    }
    public PharmacyDose savePharmacyDose(PharmacyDose pharmacyDose) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyDose);
        return pharmacyDose;
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacySupplier()
     */

    public List<PharmacySupplier> getPharmacySupplier() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacySupplier.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacySupplierByUuid(String)
     */

    public PharmacySupplier getPharmacySupplierByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacySupplier.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacySupplier> regimen = criteria.list();
        if (null == regimen || regimen.isEmpty()) {
            return null;
        }
        return regimen.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacySupplierByName(String)
     */

    public PharmacySupplier getPharmacySupplierByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacySupplier.class)
                .add(Expression.eq("name", name));

        @SuppressWarnings("unchecked")
        List<PharmacySupplier> pharmacySupplier = criteria.list();
        if (null == pharmacySupplier || pharmacySupplier.isEmpty()) {
            return null;
        }
        return pharmacySupplier.get(0);
    }
    public List<PharmacyDose> getPharmacyDose() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyDose.class)
                .add(Expression.eq("voided", false));
        return criteria.list();
    }
    public PharmacyDose getPharmacyDoseByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyDose.class)
                .add(Expression.eq("uuid", uuid));
        @SuppressWarnings("unchecked")
        List<PharmacyDose> regimen = criteria.list();
        if (null == regimen || regimen.isEmpty()) {
            return null;
        }
        return regimen.get(0);
    }
    public PharmacyDose getPharmacyDoseByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyDose.class)
                .add(Expression.eq("name", name));

        @SuppressWarnings("unchecked")
        List<PharmacyDose> pharmacyDose = criteria.list();
        if (null == pharmacyDose || pharmacyDose.isEmpty()) {
            return null;
        }
        return pharmacyDose.get(0);
    }
    public PharmacyDose getPharmacyDoseByID(Integer ID){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyDose.class)
                .add(Expression.eq("id", ID));
        PharmacyDose dose=(PharmacyDose)criteria.uniqueResult();
        return dose;
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyTransactionTypes(org.openmrs.module.pharmacy.model.PharmacyTransactionTypes)
     */

    public PharmacyTransactionTypes savePharmacyTransactionTypes(PharmacyTransactionTypes pharmacyTransactionType) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyTransactionType);

        return pharmacyTransactionType;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyTransactionTypes()
     */

    public List<PharmacyTransactionTypes> getPharmacyTransactionTypes() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyTransactionTypes.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyTransactionTypesByUuid(String)
     */

    public PharmacyTransactionTypes getPharmacyTransactionTypesByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyTransactionTypes.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyTransactionTypes> pharmacyTransactionTypes = criteria.list();
        if (null == pharmacyTransactionTypes || pharmacyTransactionTypes.isEmpty()) {
            return null;
        }
        return pharmacyTransactionTypes.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyTransactionTypesByName(String)
     */

    public PharmacyTransactionTypes getPharmacyTransactionTypesByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyTransactionTypes.class)
                .add(Expression.eq("name", name));

        @SuppressWarnings("unchecked")
        List<PharmacyTransactionTypes> pharmacyTransactionTypes = criteria.list();
        if (null == pharmacyTransactionTypes || pharmacyTransactionTypes.isEmpty()) {
            return null;
        }
        return pharmacyTransactionTypes.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyStoreIncoming(org.openmrs.module.pharmacy.model.PharmacyStoreIncoming)
     */

    public PharmacyStoreIncoming savePharmacyStoreIncoming(PharmacyStoreIncoming pharmacySupplier) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacySupplier);

        return pharmacySupplier;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyStoreIncoming(org.openmrs.module.pharmacy.model.PharmacyStoreIncoming)
     */

    public boolean savePharmacyStoreIncoming(List<PharmacyStoreIncoming> pharmacySupplier) {

        for (PharmacyStoreIncoming pharmacyOs : pharmacySupplier) {
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacyOs);
        }

        return true;


    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyStoreIncoming()
     */

    public List<PharmacyStoreIncoming> getPharmacyStoreIncoming() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStoreIncoming.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyStoreIncomingByUuid(String)
     */

    public PharmacyStoreIncoming getPharmacyStoreIncomingByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStoreIncoming.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyStoreIncoming> pharmacyStoreIncoming = criteria.list();
        if (null == pharmacyStoreIncoming || pharmacyStoreIncoming.isEmpty()) {
            return null;
        }
        return pharmacyStoreIncoming.get(0);
    }


    public List<PharmacyStoreIncoming> getPharmacyStoreIncomingByLocation(PharmacyLocations uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStoreIncoming.class)
                .add(Expression.eq("location", uuid));

        return criteria.list();
    }
    public List<PharmacyStoreOutgoing> getPharmacyStoreOutgoingByLocation(PharmacyLocations uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStoreIncoming.class)
                .add(Expression.eq("destination", uuid));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyStoreOutgoing(org.openmrs.module.pharmacy.model.PharmacyStoreOutgoing)
     */

    public PharmacyStoreOutgoing savePharmacyStoreOutgoing(PharmacyStoreOutgoing pharmacyStoreOutgoing) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyStoreOutgoing);

        return pharmacyStoreOutgoing;
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyStoreOutgoing(org.openmrs.module.pharmacy.model.PharmacyStoreOutgoing)
     */

    public boolean savePharmacyStoreOutgoing(List<PharmacyStoreOutgoing> pharmacyStoreOutgoing) {

        for (PharmacyStoreOutgoing pharmacyStore : pharmacyStoreOutgoing) {
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacyStore);
        }
        return false;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyStoreOutgoing()
     */

    public List<PharmacyStoreOutgoing> getPharmacyStoreOutgoing() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStoreOutgoing.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }


    public PharmacyStoreOutgoing getPharmacyStoreOutgoingByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStoreOutgoing.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyStoreOutgoing> pharmacyStoreOutgoing = criteria.list();
        if (null == pharmacyStoreOutgoing || pharmacyStoreOutgoing.isEmpty()) {
            return null;
        }
        return pharmacyStoreOutgoing.get(0);
    }

    public SessionFactory getSession() {
        return sessionFactory;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#setPharmacyLocation(String)
     */

    public boolean setPharmacyLocation(String doses) {
        // TODO Auto-generated method stub
        loc.setName(doses);
        return true;
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#setPharmacyLocationNull()
     */

    public boolean setPharmacyLocationNull() {
        // TODO Auto-generated method stub
        loc.setName(null);
        return true;
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyLocation()
     */

    public String getPharmacyLocation() {
        if(loc.getName().equalsIgnoreCase("none")){

            return "none";

        }
        else
            return	loc.getName();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyOrders(java.util.List)
     */

    public boolean savePharmacyOrders(List<PharmacyOrders> pharmacyOrders) {
        for (PharmacyOrders pharmacyOrderss : pharmacyOrders) {
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacyOrderss);
        }
        return false;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyCategory(org.openmrs.module.pharmacy.model.PharmacyCategory)
     */

    public PharmacyCategory savePharmacyCategory(PharmacyCategory pharmacyCategory) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyCategory);

        return pharmacyCategory;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyCategory()
     */

    public List<PharmacyCategory> getPharmacyCategory() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyCategory.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyCategoryByUuid(String)
     */

    public PharmacyCategory getPharmacyCategoryByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyCategory.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyCategory> pharmacyCategory = criteria.list();
        if (null == pharmacyCategory || pharmacyCategory.isEmpty()) {
            return null;
        }
        return pharmacyCategory.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyCategoryByName(String)
     */

    public PharmacyCategory getPharmacyCategoryByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyCategory.class)
                .add(Expression.eq("name", name));

        @SuppressWarnings("unchecked")
        List<PharmacyCategory> pharmacyCategory = criteria.list();
        if (null == pharmacyCategory || pharmacyCategory.isEmpty()) {
            return null;
        }
        return pharmacyCategory.get(0);
    }




    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyGeneralVariables(org.openmrs.module.pharmacy.model.PharmacyGeneralVariables)
     */

    public PharmacyGeneralVariables savePharmacyGeneralVariables(PharmacyGeneralVariables pharmacyGeneralVariables) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyGeneralVariables);

        return pharmacyGeneralVariables;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyGeneralVariables()
     */

    public List<PharmacyGeneralVariables> getPharmacyGeneralVariables() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyGeneralVariables.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyGeneralVariablesByUuid(String)
     */

    public PharmacyGeneralVariables getPharmacyGeneralVariablesByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyGeneralVariables.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyGeneralVariables> pharmacyGeneralVariables = criteria.list();
        if (null == pharmacyGeneralVariables || pharmacyGeneralVariables.isEmpty()) {
            return null;
        }
        return pharmacyGeneralVariables.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyGeneralVariablesByName(String)
     */

    public PharmacyGeneralVariables getPharmacyGeneralVariablesByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyGeneralVariables.class)
                .add(Expression.eq("name", name));

        @SuppressWarnings("unchecked")
        List<PharmacyGeneralVariables> pharmacyGeneralVariables = criteria.list();
        if (null == pharmacyGeneralVariables || pharmacyGeneralVariables.isEmpty()) {
            return null;
        }
        return pharmacyGeneralVariables.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyLocations(org.openmrs.module.pharmacy.model.PharmacyLocations)
     */

    public PharmacyLocations savePharmacyLocations(PharmacyLocations pharmacyLocations) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyLocations);

        return pharmacyLocations;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyLocations()
     */

    public List<PharmacyLocations> getPharmacyLocations() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyLocations.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyLocationsByUuid(String)
     */

    public PharmacyLocations getPharmacyLocationsByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyLocations.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyLocations> pharmacyLocations = criteria.list();
        if (null == pharmacyLocations || pharmacyLocations.isEmpty()) {
            return null;
        }
        return pharmacyLocations.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyLocationsByName(String)
     */

    public PharmacyLocations getPharmacyLocationsByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyLocations.class)
                .add(Expression.eq("name", name));

        @SuppressWarnings("unchecked")
        List<PharmacyLocations> pharmacyLocations = criteria.list();
        if (null == pharmacyLocations || pharmacyLocations.isEmpty()) {
            return null;
        }
        return pharmacyLocations.get(0);
    }


    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyLocations(org.openmrs.module.pharmacy.model.PharmacyLocations)
     */

    public PharmacyLocationUsers savePharmacyLocationUsers(PharmacyLocationUsers pharmacyLocationUsers) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyLocationUsers);

        return pharmacyLocationUsers;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyLocations()
     */

    public List<PharmacyLocationUsers> getPharmacyLocationUsers() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyLocationUsers.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyLocationsByUuid(String)
     */

    public PharmacyLocationUsers getPharmacyLocationUsersByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyLocationUsers.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyLocationUsers> pharmacyLocationsUsers = criteria.list();
        if (null == pharmacyLocationsUsers || pharmacyLocationsUsers.isEmpty()) {
            return null;
        }
        return pharmacyLocationsUsers.get(0);
    }


    public List<PharmacyLocationUsers> getPharmacyLocationUsersByUserName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyLocationUsers.class)
                .add(Expression.eq("userName", name));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#saveDrugMaxMin(org.openmrs.module.pharmacy.model.DrugMaxMin)
     */

    public DrugMaxMin saveDrugMaxMin(DrugMaxMin drugMaxMin) {
        sessionFactory.getCurrentSession().saveOrUpdate(drugMaxMin);

        return drugMaxMin;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugMaxMin()
     */

    public List<DrugMaxMin> getDrugMaxMin() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugMaxMin.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugMaxMinByUuid(String)
     */

    public DrugMaxMin getDrugMaxMinByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugMaxMin.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<DrugMaxMin> drugMaxMin = criteria.list();
        if (null == drugMaxMin || drugMaxMin.isEmpty()) {
            return null;
        }
        return drugMaxMin.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugMaxMinByDrug(Drug)
     */

    public DrugMaxMin getDrugMaxMinByDrug(Drug name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugMaxMin.class)
                .add(Expression.eq("drug", name));

        @SuppressWarnings("unchecked")
        List<DrugMaxMin> drugMaxMin = criteria.list();
        if (null == drugMaxMin || drugMaxMin.isEmpty()) {
            return null;
        }
        return drugMaxMin.get(0);
    }


    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyStoreApproved(org.openmrs.module.pharmacy.model.PharmacyStoreApproved)
     */

    public PharmacyStoreApproved savePharmacyStoreApproved(PharmacyStoreApproved pharmacyStoreApproved) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyStoreApproved);

        return pharmacyStoreApproved;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyStoreApproved(org.openmrs.module.pharmacy.model.PharmacyStoreApproved)
     */

    public boolean savePharmacyStoreApproved(List<PharmacyStoreApproved> pharmacyStoreApproved) {

        for (PharmacyStoreApproved pharmacyStore : pharmacyStoreApproved) {
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacyStore);
        }
        return false;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyStoreApproved()
     */

    public List<PharmacyStoreApproved> getPharmacyStoreApproved() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStoreApproved.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyStoreApprovedByUuid(String)
     */

    public PharmacyStoreApproved getPharmacyStoreApprovedByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStoreApproved.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyStoreApproved> pharmacyStoreApproved = criteria.list();
        if (null == pharmacyStoreApproved || pharmacyStoreApproved.isEmpty()) {
            return null;
        }
        return pharmacyStoreApproved.get(0);
    }

    public List<PharmacyStoreApproved> getPharmacyStoreApprovedByLocation(PharmacyLocations uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStoreApproved.class)
                .add(Expression.eq("destination", uuid));

        return criteria.list();
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyOrders(java.util.List)
     */

    public boolean savePharmacyDrugOrders(List<PharmacyDrugOrder> pharmacyOrders) {
        for (PharmacyDrugOrder pharmacyDrugOrder : pharmacyOrders) {
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacyDrugOrder);
        }
        return true;
    }

    public PharmacyDrugOrder getPharmacyDrugOrdersByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyDrugOrder.class)
                .add(Expression.eq("uuid", uuid));
        PharmacyDrugOrder pharmacyDrugOrder=(PharmacyDrugOrder)criteria.uniqueResult();
        return pharmacyDrugOrder;
    }
    public PharmacyDrugOrder getPharmacyDrugOrdersByOrders(PharmacyOrders uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyDrugOrder.class)
                .add(Expression.eq("orderUuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyDrugOrder> pharmacyDrugOrder = criteria.list();
        if (null == pharmacyDrugOrder || pharmacyDrugOrder.isEmpty()) {
            return null;
        }
        return pharmacyDrugOrder.get(0);
    }
    public PharmacyDrugOrder getPharmacyDrugOrdersByDrugExtraUUID(DrugExtra uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyDrugOrder.class)
                .add(Expression.eq("drugUuid", uuid));
        PharmacyDrugOrder pharmacyDrugOrder =(PharmacyDrugOrder)criteria.uniqueResult();
        return pharmacyDrugOrder;
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyOrders(java.util.List)
     */

    public boolean savePharmacyDrugOrderExtra(PharmacyDrugOrderExtra pharmacyDrugOrderExtra) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyDrugOrderExtra);
        return true;
    }


    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyDrugOrderExtra()
     */

    public List<PharmacyDrugOrderExtra> getPharmacyDrugOrderExtra() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyDrugOrderExtra.class)
                .add(Expression.eq("voided", false));

        return criteria.list();

    }

    /**
     */

    public PharmacyDrugOrderExtra getPharmacyDrugOrderExtraByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyDrugOrderExtra.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyDrugOrderExtra> pharmacyDrugOrderExtra = criteria.list();
        if (null == pharmacyDrugOrderExtra || pharmacyDrugOrderExtra.isEmpty()) {
            return null;
        }
        return pharmacyDrugOrderExtra.get(0);
    }


    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#saveDrugDispenseSettings(org.openmrs.module.pharmacy.model.DrugDispenseSettings)
     */

    public DrugExtra saveDrugExtra(List<DrugExtra> drugExtra) {
        for (DrugExtra pharmacy : drugExtra) {
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacy);
        }
        return null;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugDispenseSettings()
     */

    public List<DrugExtra> getDrugExtra() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugExtra.class)
                .add(Expression.eq("voided", false))
                .add(Expression.isNotNull("receipt"));
        return criteria.list();
    }
    public List<DrugExtra> getUnprocessedReceiptsByEncounterUUID(String encounterUUID) {
        /*Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugExtra.class)
                .createAlias("pharmacyEncounter", "pharmacyEncounter")
                .add(Restrictions.like("pharmacyEncounter.uuid", encounterUUID))
                .addOrder(Order.asc("dateCreated"));
        return criteria.list(); */

        String sql="SELECT pde.* FROM pharmacy_drug_extra pde  WHERE pde.encounter_uuid like :encounterUUID ";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(DrugExtra.class);
        query.setParameter("encounterUUID",encounterUUID);
        List results = query.list();
        return results;
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugExtraRange(Date minDate, Date maxDate)
     */
    public List<DrugExtra> getDrugExtraRange(Date minDate, Date maxDate) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugExtra.class)
                .add(Expression.eq("voided", false))
                .add(Expression.isNotNull("receipt"))
                .add(Expression.isNotNull("drug"))
                .add(Expression.between("dateCreated", minDate,maxDate));
        //log.info(criteria.list());
        return criteria.list();
    }

    public List<DrugExtra> getDrugRange(Date minDate, Date maxDate,String locationUUID) {
        String sql = "SELECT pde.* FROM pharmacy_drug_extra pde INNER JOIN drug d ON d.drug_id=pde.drug_id WHERE location_uuid like :location AND encounter_date BETWEEN :sDate AND :eDate AND pde.drug_id IS NOT NULL  GROUP BY pde.drug_id order by d.name";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(DrugExtra.class);
        query.setParameter("sDate", minDate);
        query.setParameter("eDate", maxDate);
        query.setParameter("location", locationUUID);
        List results = query.list();
        return results;

    }
    public List<PharmacyEncounter> getEncountersRange(Date minDate,Date maxDate,String location) {
        String sql="SELECT pe.* FROM pharmacy_encounter pe  WHERE pe.location_uuid like :loc " +
                "AND pe.datecreated BETWEEN :sDate AND :eDate GROUP BY pe.regimenCode";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(PharmacyEncounter.class);
        query.setParameter("sDate",minDate);
        query.setParameter("eDate",maxDate);
        query.setParameter("loc",location);
        List results = query.list();
        return results;

    }
    public List<PharmacyEncounter> getEncountersBetweenDates(Date minDate,Date maxDate,String location) {
        String sql="SELECT pe.* FROM pharmacy_encounter pe  WHERE pe.location_uuid like :loc " +
                "AND pe.encounter_datetime BETWEEN :sDate AND :eDate";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(PharmacyEncounter.class);
        query.setParameter("sDate",minDate);
        query.setParameter("eDate",maxDate);
        query.setParameter("loc",location);
        List results = query.list();
        return results;

    }
    public List<PharmacyEncounter> getDetailedEncountersRange(Date minDate,Date maxDate,String location) {
        String sql="SELECT pe.* FROM pharmacy_encounter pe  WHERE pe.location_uuid like :loc AND pe.datecreated BETWEEN :sDate AND :eDate ";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(PharmacyEncounter.class);
        query.setParameter("sDate",minDate);
        query.setParameter("eDate",maxDate);
        query.setParameter("loc",location);
        List results = query.list();
        return results;

    }
    public PharmacyEncounter getCurrentPatientRegimen(String patientUUID) {
        String sql="SELECT pe.* FROM pharmacy_encounter pe  WHERE pe.patient_uuid like :pUUID " +
                "AND (pe.form_name like 'ADULTHIV' OR pe.form_name like 'PEDIATRICARV') ORDER BY pe.pharmacy_encounter_id DESC LIMIT 1";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(PharmacyEncounter.class);
        query.setParameter("pUUID",patientUUID);
        PharmacyEncounter pharmacyEncounter = (PharmacyEncounter)query.uniqueResult();
        return pharmacyEncounter;

    }
    public Integer  getNumberOfPatientsOnRegimen(Date startDate,Date endDate,String regimenCode,String locationUUID){
        String sql="SELECT COUNT(DISTINCT patient_uuid) FROM pharmacy_encounter WHERE regimenCode LIKE :regimen AND location_uuid like :loc AND dateCreated BETWEEN :sDate AND :eDate";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("regimen",regimenCode);
        query.setParameter("sDate", startDate);
        query.setParameter("eDate", endDate);
        query.setParameter("loc", locationUUID);
        return ((Number) query.uniqueResult()).intValue();

    }
    public Integer  getDrugsDispensedWithinPeriodRange(Date startDate,Date endDate, Integer drugID,String locationUUID){
        String sql="SELECT SUM(quantity_sold) FROM pharmacy_drug_extra WHERE drug_id = :drug AND location_uuid LIKE :location AND encounter_date BETWEEN :sDate AND :eDate";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("drug",drugID);
        query.setParameter("location",locationUUID);
        query.setParameter("sDate", startDate);
        query.setParameter("eDate", endDate);
        Integer drugsSold=0;
        if(query.uniqueResult() !=null){
            drugsSold=Integer.valueOf(query.uniqueResult().toString());
        }
        return drugsSold;
    }
    public Double  getDrugTotalCashCollectedWithinPeriodRange(Date startDate,Date endDate, Integer drugID,String locationUUID){
        String sql="SELECT SUM(amount) FROM pharmacy_drug_extra WHERE drug_id = :drug AND location_uuid LIKE :location AND encounter_date BETWEEN :sDate AND :eDate";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("drug",drugID);
        query.setParameter("location",locationUUID);
        query.setParameter("sDate", startDate);
        query.setParameter("eDate", endDate);
        Double amount=0.0;
        if(query.uniqueResult() !=null){
            amount=Double.valueOf(query.uniqueResult().toString());
        }
        return amount;
    }
    public Integer getAmountWaivedWithinPeriodRange(Date startDate, Date endDate, Integer drugID, String locationUUID){
        String sql="SELECT SUM(wAmount) FROM pharmacy_drug_extra WHERE drug_id = :drug AND location_uuid LIKE :location AND encounter_date BETWEEN :sDate AND :eDate";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("drug",drugID);
        query.setParameter("location",locationUUID);
        query.setParameter("sDate", startDate);
        query.setParameter("eDate", endDate);
        Integer amountWaived=0;
        if(query.uniqueResult() !=null){
            amountWaived=((Number) query.uniqueResult()).intValue();
        }
        return amountWaived;
    }
    public Double  getDiscountOnDrugsWithinPeriodRange(Date startDate,Date endDate, String drugID,String locationUUID){
        String sql="SELECT SUM(discount) FROM pharmacy_drug_extra WHERE drug_id = :drug AND location_uuid LIKE :location AND encounter_date BETWEEN :sDate AND :eDate";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("drug",drugID);
        query.setParameter("location",locationUUID);
        query.setParameter("sDate", startDate);
        query.setParameter("eDate", endDate);
        Double discount=0.0;
        if(query.uniqueResult() !=null){
            discount=((Double) query.uniqueResult());
        }
        return discount;
    }
    public Integer  getNumberOfTimesDrugWaivedWithinPeriodRange(Date startDate,Date endDate, Integer drugID,String locationUUID){
        String sql="SELECT COUNT(*) FROM pharmacy_drug_extra WHERE drug_id = :drug AND location_uuid LIKE :location AND encounter_date BETWEEN :sDate AND :eDate AND wAmount > 0";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("drug",drugID);
        query.setParameter("location",locationUUID);
        query.setParameter("sDate", startDate);
        query.setParameter("eDate", endDate);
        Integer timesWaived=0;
        if(query.uniqueResult() !=null){
            timesWaived=Integer.valueOf(query.uniqueResult().toString());
        }
        return timesWaived;
    }
    public String  getPatientByIdentifier(String identifier){
        String sql="SELECT patient_id FROM patient_identifier WHERE identifier LIKE :patient_identifier ";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("patient_identifier",identifier+"%");
        return query.uniqueResult().toString();

    }
    public List<PharmacyStoreIncoming> getDrugQuantityAfterLastStockTake(Date minDate, Date maxDate,String uuid) {
        String sql = "SELECT SUM(quantity_in) FROM pharmacy_inventory_incoming where pharmacy_drug_uuid like :'uid' AND dateCreated BETWEEN :sDate AND :eDate";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(PharmacyStoreIncoming.class);
        query.setParameter("sDate", minDate);
        query.setParameter("eDate", maxDate);
        query.setParameter("uid", uuid);
        List results = query.list();
        //log.info(results);
        return results;

    }

    public DrugExtra getDrugExtraByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugExtra.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<DrugExtra> drugExtra = criteria.list();
        if (null == drugExtra || drugExtra.isEmpty()) {
            return null;
        }
        return drugExtra.get(0);
    }


    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugDispenseSettingsByBatch(int)
     */

    public DrugExtra getDrugExtraByBatch(int name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugExtra.class)
                .add(Expression.eq("batchId", name));

        @SuppressWarnings("unchecked")
        List<DrugExtra> drugExtra = criteria.list();
        if (null == drugExtra || drugExtra.isEmpty()) {
            return null;
        }
        return drugExtra.get(0);
    }
    public PharmacyStore getBatchNoByNo(int batchno) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Expression.eq("batchNo", batchno));

        @SuppressWarnings("unchecked")
        List<PharmacyStore> pharmacyStores = criteria.list();
        if (null == pharmacyStores || pharmacyStores.isEmpty()) {
            return null;
        }
        return pharmacyStores.get(0);

    }
    public PharmacyTemporaryInventory saveTemporaryInventory(PharmacyTemporaryInventory pharmacyTemporaryInventory){
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyTemporaryInventory);
        return  pharmacyTemporaryInventory;
    }
    public List<PharmacyStore> getPharmacyStoreByLocation(PharmacyLocations locationUUID){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Expression.eq("voided", false))
                .add(Restrictions.eq("location",locationUUID))
                .createAlias("drugs","d")
                .addOrder(Order.asc("d.name"));
        List results=criteria.list();
        return results;
    }
    public List<PharmacyStore> getPharmacyStoreByLocationPlusRetired(PharmacyLocations locationUUID){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Restrictions.eq("location",locationUUID))
                .createAlias("drugs","d")
                .addOrder(Order.asc("d.name"));
        List results=criteria.list();
        return results;
    }

    public List<PharmacyStore> getInventoryByLocation(String locationUUID){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Expression.eq("voided", false))
                .add(Restrictions.eq("location",locationUUID))
                .add(Restrictions.gt("quantity",0))
                .createAlias("drugs","d")
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .addOrder(Order.asc("d.name"));
        List results=criteria.list();
        return results;
    }

    public PharmacyEncounter getLastPharmacyEncounterByPatientUUID(Patient patientUUID){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyEncounter.class)
                .add(Restrictions.eq("person",patientUUID));
        criteria.addOrder(Order.desc("id"));
        criteria.setMaxResults(1);
        PharmacyEncounter encounter=(PharmacyEncounter)criteria.uniqueResult();
        return encounter;
    }
    public List<DrugExtra> getDrugExtraByPharmacyEncounter(PharmacyEncounter encounter){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(DrugExtra.class)
                .add(Restrictions.eq("pharmacyEncounter",encounter));
        List<DrugExtra> drugExtra=criteria.list();
        return drugExtra;
    }
    public PharmacyStore getPharmacyStoreByDrugName(PharmacyLocations locationUUID,Drug drug){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Expression.eq("voided", false))
                .add(Restrictions.eq("location",locationUUID))
                .add(Restrictions.eq("drugs",drug))
                .add(Restrictions.eq("voided",false));
        criteria.setMaxResults(1);
        PharmacyStore pharmacyStore=(PharmacyStore)criteria.uniqueResult();
        return pharmacyStore;
    }
    public List<S11> getS11WithinDateRange(PharmacyLocations location, Date startDate, Date endDate){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(S11.class)
                .add(Restrictions.ge("dateCreated",startDate))
                .add(Restrictions.lt("dateCreated",endDate))
                .add(Restrictions.eq("location",location));
        List<S11> s11List=criteria.list();
        return s11List;
    }
    public boolean savePharmacyS11(S11 pharmacyS11){
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyS11);
        return true;
    }
    public List<DrugTransactions>  getPharmacyDrugTransactionsByS11NO(String s11No,PharmacyLocations pharmacyLocation){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(DrugTransactions.class)
                .add(Restrictions.eq("s11",s11No))
                .add(Restrictions.eq("location",pharmacyLocation));

        List<DrugTransactions> drugTransactionsList=criteria.list();
        return drugTransactionsList;

    }
    public boolean saveGeneratedInventoryQuantities(List<GeneratePharmacyInventoryQuantities> stockQuantities) {
        for (GeneratePharmacyInventoryQuantities inventoryInstance : stockQuantities) {
            sessionFactory.getCurrentSession().saveOrUpdate(inventoryInstance);
        }
        return true;
    }

    public List<PharmacyStore> getDrugTransactionsBetweenRange(Date startDate, Date endDate,String pharmacyLocation){
        String sql="SELECT pi.* FROM pharmacy_inventory pi INNER JOIN drug d ON d.uuid=pi.pharmacy_drug_uuid WHERE location_uuid like :locUUID AND voided=0 GROUP BY pi.pharmacy_drug_uuid ORDER BY d.name";
        SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(PharmacyStore.class);
        query.setParameter("locUUID",pharmacyLocation);
        /*Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Restrictions.eq("location", pharmacyLocation))
                .add(Restrictions.gt("quantity", 0))
                .add(Restrictions.eq("voided", false))
                .createAlias("drugs", "d")
                .addOrder(Order.asc("d.name"));*/
        List <PharmacyStore> pharmacyStoreList=query.list();
        return  pharmacyStoreList;
    }
    public Integer computeQuantityOfDrugsReceivedWithinDateRange(Date startDate, Date endDate,PharmacyLocations pharmacyLocation,Drug drug){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(DrugTransactions.class)
                .add(Restrictions.eq("location", pharmacyLocation))
                .add(Restrictions.ge("dateCreated", startDate))
                .add(Restrictions.lt("dateCreated", endDate))
                .add(Restrictions.eq("drugs", drug))
                .add(Restrictions.eq("comment", "New entry"))
                .add(Restrictions.eq("voided", false))
                .setProjection(Projections.sum("quantityIn"));

        Integer sumOfDrugReceived=0;
        if(criteria.uniqueResult() !=null) {
            sumOfDrugReceived = Integer.valueOf(criteria.uniqueResult().toString());
        }
        return sumOfDrugReceived;
    }

    public Integer computeQuantityOfDrugsDispensedWithinDateRange(Date startDate, Date endDate,String locationUUID,String drugID){
        String sql="SELECT SUM(quantity_given) FROM pharmacy_drug_order WHERE drug = :drugUUID AND pharmacy_location LIKE :location AND dateCreated BETWEEN :sDate AND :eDate";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("drugUUID",drugID);
        query.setParameter("location", locationUUID);
        query.setParameter("sDate", startDate);
        query.setParameter("eDate", endDate);
        Integer quantityDispensed=0;
        if(query.uniqueResult() !=null){
            quantityDispensed=Integer.valueOf(query.uniqueResult().toString());
        }
        return quantityDispensed;
    }
    public List<PharmacyDrugOrder> pharmacyDrugOrdersBetweenTwoDates(Date startDate, Date endDate,PharmacyLocations pharmacyLocation,Drug drug){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyDrugOrder.class)
                .add(Restrictions.eq("drug", drug))
                .add(Restrictions.eq("pharmacyLocation", pharmacyLocation))
                .add(Restrictions.ge("dateCreated", startDate))
                .add(Restrictions.lt("dateCreated", endDate));
        List<PharmacyDrugOrder> pharmacyDrugOrderList=criteria.list();
        return pharmacyDrugOrderList;
    }
    public String getDrugTransactionByS11AndDrug(PharmacyLocations location, Drug drug,String s11){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(DrugTransactions.class)
                .add(Restrictions.eq("drugs", drug))
                .add(Restrictions.eq("s11", s11))
                .add(Restrictions.eq("comment","New entry"))
                .add(Restrictions.eq("location", location))
                .setProjection(Projections.distinct(Projections.property("s11")));
        String drugTransaction=(String)criteria.uniqueResult();
        return drugTransaction;
    }
    public Integer getDrugS11QuantityReceived(PharmacyLocations location, Drug drug,String s11){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(DrugTransactions.class)
                .add(Restrictions.eq("drugs", drug))
                .add(Restrictions.eq("s11", s11))
                .add(Restrictions.eq("comment", "New entry"))
                .add(Restrictions.eq("location", location))
                .setProjection(Projections.sum("quantityIn"));
        Integer drugQuantityReceived=Integer.valueOf(criteria.uniqueResult().toString());
        return drugQuantityReceived;
    }
    public DrugTransactions getDrugTransactionByLocationAndDrug(PharmacyLocations location, Drug drug, String transactionUUID){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(DrugTransactions.class)
                .add(Restrictions.eq("drugs", drug))
                .add(Restrictions.eq("location",location))
                .add(Restrictions.eq("uuid", transactionUUID));
        DrugTransactions drugTransaction=(DrugTransactions)criteria.uniqueResult();
        return drugTransaction;
    }
    public List<DrugTransactions> getTransferedTransactionsdBetweenDates(Date startDate, Date endDate,String pharmacyLocationUUID){
        String sql="SELECT * FROM pharmacy_drug_transactions WHERE location_uuid like:location AND comment like:comment1 OR comment like:comment2 AND dateCreated BETWEEN :sDate AND :eDate AND voided=0";
        SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(DrugTransactions.class);
        query.setParameter("location",pharmacyLocationUUID);
        query.setParameter("comment1","Transfer");
        query.setParameter("comment2","Issued");
        query.setParameter("sDate",startDate);
        query.setParameter("eDate",endDate);

        List<DrugTransactions> drugTransactions=query.list();
        return drugTransactions;
    }
    public List<DrugTransactions> getReceivedDrugTransactionsdBetweenDates(Date startDate, Date endDate,String pharmacyLocationUUID){
        String sql="SELECT * FROM pharmacy_drug_transactions WHERE location_uuid like:location AND comment like:comment  AND dateCreated BETWEEN :sDate AND :eDate AND voided=0";
        SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(DrugTransactions.class);
        query.setParameter("location",pharmacyLocationUUID);
        query.setParameter("comment","Received");
        query.setParameter("sDate",startDate);
        query.setParameter("eDate",endDate);

        List<DrugTransactions> drugTransactions=query.list();
        return drugTransactions;
    }

    public List<S11> getDistinctS11WithinDateRange(String locUUID, Date startDate, Date endDate){
        String sql = "SELECT p.* FROM pharmacy_s11 p WHERE locationUUID like :uid AND datecreated BETWEEN :sDate AND :eDate GROUP BY p.s11No";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(S11.class);
        query.setParameter("sDate", startDate);
        query.setParameter("eDate", endDate);
        query.setParameter("uid", locUUID);
        List results = query.list();
        //log.info(results);
        return results;
    }
    public GeneratePharmacyInventoryQuantities getDrugInventoryOpeningStockByDateAndLocation(String drugUUID,Date startdate,Date endDate,String locationUUID){
        String sql="SELECT pigd.* FROM pharmacy_inventory_generated_daily pigd WHERE pharmacy_location_uuid like :locUUID AND drug like:drugUID AND datecreated BETWEEN :sDate AND :eDate GROUP BY drug";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(GeneratePharmacyInventoryQuantities.class);
        query.setParameter("sDate", startdate);
        query.setParameter("eDate", endDate);
        query.setParameter("locUUID", locationUUID);
        query.setParameter("drugUID", drugUUID);
        GeneratePharmacyInventoryQuantities inventoryInstance=(GeneratePharmacyInventoryQuantities)query.uniqueResult();
        return inventoryInstance;
    }
    public List<DrugExtra> getWaiversWithinDateRange(String locUUID, Date startDate, Date endDate){
        String sql = "SELECT p.* FROM pharmacy_drug_extra p WHERE location_uuid like :uid AND voided=0 AND datecreated BETWEEN :sDate AND :eDate AND wAmount > 0";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(DrugExtra.class);
        query.setParameter("sDate", startDate);
        query.setParameter("eDate", endDate);
        query.setParameter("uid", locUUID);
        List results = query.list();
        //log.info(results);
        return results;
    }
    public List<DrugExtra> getDiscountsWithinDateRange(String locUUID, Date startDate, Date endDate){
        String sql = "SELECT p.* FROM pharmacy_drug_extra p WHERE location_uuid like :uid AND voided=0 AND datecreated BETWEEN :sDate AND :eDate AND discount > 0";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(DrugExtra.class);
        query.setParameter("sDate", startDate);
        query.setParameter("eDate", endDate);
        query.setParameter("uid", locUUID);
        List results = query.list();
        //log.info(results);
        return results;
    }
    public List<PharmacyStore> getAllPharmacyStorePlusRetired(PharmacyLocations locationUUID){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Restrictions.eq("location",locationUUID))
                .createAlias("drugs","d")
                .addOrder(Order.asc("d.name"));
        List results=criteria.list();
        return results;
    }
    public PharmacyDrugOrder getHIVPatientLastVisitPharmacyDrugOrder(String patientID,String formName){
        String sql="SELECT p.* FROM pharmacy_drug_order p WHERE patient_uuid LIKE :pUid AND formname NOT LIKE :fName ORDER BY pharmacy_drug_order_id DESC LIMIT 1";
        SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(PharmacyDrugOrder.class);
        query.setParameter("pUid", patientID);
        query.setParameter("fName",formName);
        PharmacyDrugOrder pharmacyDrugOrder=(PharmacyDrugOrder)query.uniqueResult();
        return pharmacyDrugOrder;
    }
    public List<DrugExtra> getDrugExtraRangeByDrugAndLocation(String pharmacyLocations,String drug,Date startDate,Date endDate){
        String sql="SELECT p.* FROM pharmacy_drug_extra p WHERE location_uuid like :loc AND drug_id like :d AND datecreated BETWEEN :sDate AND :eDate";
        SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(DrugExtra.class);
        query.setParameter("d", drug);
        query.setParameter("loc", pharmacyLocations);
        query.setParameter("sDate",startDate);
        query.setParameter("eDate",endDate);
        List<DrugExtra> drugExtraList=query.list();
        return  drugExtraList;
    }
    public InventoryMetaData savePharmacyInventoryMetaData(InventoryMetaData inventoryMetaData) {
        sessionFactory.getCurrentSession().saveOrUpdate(inventoryMetaData);
        return inventoryMetaData;
    }
    public InventoryMetaData getInventoryMetaDataByDrugName(Drug drugName,PharmacyLocations pharmacyLocation){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(InventoryMetaData.class)
                .add(Restrictions.eq("drug", drugName))
                .add(Restrictions.eq("location",pharmacyLocation))
                .add(Restrictions.eq("voided",false));
        InventoryMetaData inventoryMetaData=(InventoryMetaData)criteria.uniqueResult();
        return inventoryMetaData;
    }
    public InventoryMetaData getInventoryMetaDataByUUID(String UUID){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(InventoryMetaData.class)
                .add(Restrictions.eq("uuid", UUID));
        InventoryMetaData inventoryMetaData=(InventoryMetaData)criteria.uniqueResult();
        return inventoryMetaData;
    }
    public List<PharmacyStore> getPharmacyStoreForApprovalByLocation(PharmacyLocations locationUUID){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Expression.eq("voided", false))
                .add(Restrictions.eq("location",locationUUID))
                .add(Restrictions.eq("stockTakeApprovalStatus",1))
                .createAlias("drugs", "d")
                .addOrder(Order.asc("d.name"));
        List results=criteria.list();
        return results;
    }
    public List<PharmacyEncounter> getEncountersRangeByRegimenName(Date minDate,Date maxDate,String location) {
        String sql="SELECT pe.* FROM pharmacy_encounter pe  WHERE pe.location_uuid like :loc " +
                "AND pe.datecreated BETWEEN :sDate AND :eDate GROUP BY pe.regimenName";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(PharmacyEncounter.class);
        query.setParameter("sDate",minDate);
        query.setParameter("eDate",maxDate);
        query.setParameter("loc",location);
        List results = query.list();
        return results;

    }
    public List<PharmacyEncounter> getCountOfPatientsOnRegimen(String regimen,Date minDate,Date maxDate,String location) {
        String sql = "SELECT pe.* FROM pharmacy_encounter pe  WHERE pe.location_uuid like :loc " +
                "AND regimenName LIKE :regimenN AND pe.datecreated BETWEEN :sDate AND :eDate GROUP BY pe.patient_uuid";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(PharmacyEncounter.class);
        query.setParameter("sDate", minDate);
        query.setParameter("eDate", maxDate);
        query.setParameter("loc", location);
        query.setParameter("regimenN", regimen);
        List results = query.list();
        return results;
    }
    public List<PharmacyEncounter> getPharmacyEncounterListByUserBetweenTwoDate(Date startDate,Date endDate,User user,PharmacyLocations pharmacyLocation){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyEncounter.class)
                .add(Restrictions.ge("dateCreated", startDate))
                .add(Restrictions.lt("dateCreated", endDate))
                .add(Restrictions.eq("creator",user))
                .add(Restrictions.eq("location",pharmacyLocation));
        criteria.addOrder(Order.desc("id"));
        List<PharmacyEncounter>  pharmacyEncounterList=criteria.list();
        return pharmacyEncounterList;
    }
    public PharmacyLocationUsers getPharmacyLocationUserByAmrsUserNameAndLocation(String amrsUser,String pharmacylocationName){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyLocationUsers.class)
                .add(Restrictions.eq("userName",amrsUser))
                .add(Restrictions.eq("location",pharmacylocationName));

        PharmacyLocationUsers pharmacyUser=(PharmacyLocationUsers)criteria.uniqueResult();
        return pharmacyUser;
    }
    public Integer getCumilativeS11ReceivedForDrugAtLocationBetweenTwoDates(Date startDate,Date endDate,String drugUUID,String pharmacyLocationUUID){
        String sql="SELECT SUM(quantity_in) FROM pharmacy_drug_transactions WHERE pharmacy_drug_uuid LIKE:drugUID AND location_uuid LIKE:locationUUID AND comment LIKE:type AND dateCreated BETWEEN :sDate AND :eDate";
        SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("sDate", startDate);
        query.setParameter("eDate", endDate);
        query.setParameter("drugUID",drugUUID);
        query.setParameter("type","New entry");
        query.setParameter("locationUUID",pharmacyLocationUUID);
        Integer totalStockReceived=0;
        if(query.uniqueResult() !=null){
            totalStockReceived=Integer.valueOf(query.uniqueResult().toString());
        }
        return totalStockReceived;
    }
    public Integer getCumilativeDrugStockTransferedAtLocationBetweenTwoDates(Date startDate,Date endDate,String drugUUID,String pharmacyLocationUUID){
        String sql="SELECT SUM(quantity_in) FROM pharmacy_drug_transactions WHERE pharmacy_drug_uuid LIKE:drugUID AND location_uuid LIKE:locationUUID AND comment LIKE:type AND dateCreated BETWEEN :sDate AND :eDate";
        SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("sDate", startDate);
        query.setParameter("eDate", endDate);
        query.setParameter("drugUID",drugUUID);
        query.setParameter("type","Transfer");
        query.setParameter("locationUUID",pharmacyLocationUUID);
        Integer totalStockReceived=0;
        if(query.uniqueResult() !=null){
            totalStockReceived=Integer.valueOf(query.uniqueResult().toString());
        }
        return totalStockReceived;
    }
    public List<StockTransferTracker> getStockTransferTrackerNotApproved(PharmacyLocations pharmacyLocations){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(StockTransferTracker.class)
                .add(Restrictions.eq("pharmacyLocation",pharmacyLocations))
                .add(Restrictions.eq("approved",false));
        List<StockTransferTracker> stockTransferTracker=criteria.list();
        return stockTransferTracker;
    }
    public StockTransferTracker saveStockTransferTracker(StockTransferTracker stockTransferTracker) {
        sessionFactory.getCurrentSession().saveOrUpdate(stockTransferTracker);
        return stockTransferTracker;
    }
    public List<PharmacyStoreOutgoing> getPharmacyStoreOutgoingByStockTransferTracker(StockTransferTracker stockTransferTracker){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyStoreOutgoing.class)
                .add(Restrictions.eq("stockTransferTracker",stockTransferTracker));
        List<PharmacyStoreOutgoing> pharmacyStoreOutgoingList=criteria.list();
        return pharmacyStoreOutgoingList;
    }
    public StockTransferTracker getStockTransferTrackerByUUID(String uuid){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(StockTransferTracker.class)
                .add(Restrictions.eq("uuid",uuid));
        StockTransferTracker stockTransferTracker=(StockTransferTracker)criteria.uniqueResult();
        return stockTransferTracker;
    }
    public PharmacyStoreOutgoing getPharmacyOutgoingByUUID(String uuid){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyStoreOutgoing.class)
                .add(Restrictions.eq("uuid", uuid));
        PharmacyStoreOutgoing pharmacyStoreOutgoing=(PharmacyStoreOutgoing)criteria.uniqueResult();
        return pharmacyStoreOutgoing;
    }
    public Integer quantityOfDrugsIssuedThroughTranfersBetweenTwoDates(Date startDate, Date endDate,PharmacyLocations pharmacyLocation,Drug drugObject){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyStoreOutgoing.class)
                .add(Restrictions.eq("location", pharmacyLocation))
                .add(Restrictions.ge("dateCreated", startDate))
                .add(Restrictions.lt("dateCreated", endDate))
                .add(Restrictions.eq("drug", drugObject))
                .add(Restrictions.eq("voided", false))
                .add(Restrictions.eq("approved", true))
                .setProjection(Projections.sum("quantityIn"));

        Integer sumOfDrugReceived=0;
        if(criteria.uniqueResult() !=null) {
            sumOfDrugReceived = Integer.valueOf(criteria.uniqueResult().toString());
        }
        return sumOfDrugReceived;
    }
    public Integer quantityOfDrugsReceivedThroughTransfersBetweenTwoDates(Date startDate, Date endDate,PharmacyLocations pharmacyLocation,Drug drugObject){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyStoreOutgoing.class)
                .add(Restrictions.eq("destination", pharmacyLocation))
                .add(Restrictions.ge("dateCreated", startDate))
                .add(Restrictions.lt("dateCreated", endDate))
                .add(Restrictions.eq("drug", drugObject))
                .add(Restrictions.eq("voided", false))
                .add(Restrictions.eq("approved", true))
                .setProjection(Projections.sum("quantityIn"));

        Integer sumOfDrugReceived=0;
        if(criteria.uniqueResult() !=null) {
            sumOfDrugReceived = Integer.valueOf(criteria.uniqueResult().toString());
        }
        return sumOfDrugReceived;
    }
    public List<DrugTransactions>  getDrugTransactionsByDrugAndLocation(Drug drug,PharmacyLocations pharmacyLocation,Date startDate, Date endDate){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(DrugTransactions.class)
                .add(Restrictions.eq("drugs", drug))
                .add(Restrictions.eq("location", pharmacyLocation))
                .add(Restrictions.ge("dateCreated", startDate))
                .add(Restrictions.lt("dateCreated", endDate));
        List<DrugTransactions> drugTransactionsList=criteria.list();
        return drugTransactionsList;
    }
    public boolean savePharmacyDeliveryNotes(List<DeliveryNote> deliveryNotes) {
        for (DeliveryNote deliveryNoteInstance : deliveryNotes) {
            sessionFactory.getCurrentSession().saveOrUpdate(deliveryNoteInstance);
        }
        return true;
    }
    public DeliveryNoteTracker saveDeliveryNoteTracker(DeliveryNoteTracker deliveryNoteTracker) {
        sessionFactory.getCurrentSession().saveOrUpdate(deliveryNoteTracker);
        return deliveryNoteTracker;
    }
    public List<DeliveryNoteTracker> getDeliveryNoteTrackerByLocation(PharmacyLocations pharmacyLocation){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(DeliveryNoteTracker.class)
                .add(Restrictions.eq("pharmacyLocation",pharmacyLocation))
                .add(Restrictions.eq("voided",false));
        List<DeliveryNoteTracker> deliveryNoteTrackerList=criteria.list();
        return deliveryNoteTrackerList;
    }
    public List<DeliveryNote> getDeliveryNotesByDeliveryNoteTracker(DeliveryNoteTracker deliveryNoteTracker){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(DeliveryNote.class)
                .add(Restrictions.eq("deliveryNoteTracker",deliveryNoteTracker))
                .add(Restrictions.eq("voided",false));
        List<DeliveryNote> deliveryNoteList=criteria.list();
        return deliveryNoteList;
    }
    public DeliveryNoteTracker getDeliveryNoteTrackerByUUID(String uuid){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(DeliveryNoteTracker.class)
                .add(Restrictions.eq("uuid",uuid));
        DeliveryNoteTracker deliveryNoteTracker=(DeliveryNoteTracker)criteria.uniqueResult();
        return  deliveryNoteTracker;
    }
    public List<PharmacyFingerPrint> getAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyFingerPrint.class);
        //criteria.add(Restrictions.eq("voided", false));
        return criteria.list();
    }
    public  boolean saveOpeningStockList(List<PharmacyOpeningStock> pharmacyOpeningStocks){
        for(PharmacyOpeningStock pharmacyOpeningStock:pharmacyOpeningStocks){
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacyOpeningStock);
        }
        return true;
    }
    public Date getLastOpeningStockGenerationDate(PharmacyLocations location){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyOpeningStock.class);
        criteria.add(Expression.eq("location",location));
        criteria.setProjection(Projections.max("date"));
        Date date=(Date)criteria.uniqueResult();
        return date;
    }
    public  boolean saveStockAdjustmentList(List<PharmacyStockAdjustment> pharmacyStockAdjustmentList){
        for(PharmacyStockAdjustment pharmacyStockAdjustment:pharmacyStockAdjustmentList){
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacyStockAdjustment);
        }
        return true;
    }
    public List<PharmacyOpeningStock> getOpeningStockListBetweenDatesByDrug(Drug drug,Date startDate,Date endDate,PharmacyLocations location){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyOpeningStock.class);
        criteria.add(Expression.eq("location",location));
        criteria.add(Expression.gt("date", startDate));
        criteria.add(Expression.lt("date", endDate));
        criteria.add(Expression.eq("drug",drug));
        return criteria.list();
    }
    public PharmacyOpeningStock saveOpeningStock(PharmacyOpeningStock pharmacyOpeningStock){
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyOpeningStock);
        return pharmacyOpeningStock;
    }
    public PharmacyOpeningStock getOpeningStockByDrugAndDate(Date date,PharmacyLocations location,Drug drug){
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(PharmacyOpeningStock.class);
        criteria.add(Expression.eq("location", location));
        criteria.add(Expression.eq("date", date));
        criteria.add(Expression.eq("drug",drug));
        PharmacyOpeningStock openingStock=(PharmacyOpeningStock)criteria.uniqueResult();
        return openingStock;
    }
}



















