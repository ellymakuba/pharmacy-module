/**
 *
 */
package org.openmrs.module.pharmacy.dao.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Drug;
import org.openmrs.Person;
import org.openmrs.module.pharmacy.dao.PharmacyDAO;
import org.openmrs.module.pharmacy.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyEncounterByUuid(String)
     */

    public PharmacyEncounter getPharmacyEncounterByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounter.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyEncounter> pharmacyEncounter = criteria.list();
        if (null == pharmacyEncounter || pharmacyEncounter.isEmpty()) {
            return null;
        }
        return pharmacyEncounter.get(0);
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyEncounterListByPatientId(Person)
     */
    @SuppressWarnings("unchecked")
    public List<PharmacyEncounter> getPharmacyEncounterListByPatientId(Person id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounter.class)
                .add(Expression.eq("person", id));


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

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyInventory(org.openmrs.module.pharmacy.model.PharmacyStore)
     */

    public PharmacyStore savePharmacyInventory(PharmacyStore pharmacyStore) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyStore);

        return pharmacyStore;
    }


    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyInventory(org.openmrs.module.pharmacy.model.PharmacyStore)
     */

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
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyInventoryByUuid(String)
     */

    public PharmacyStore getPharmacyInventoryByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyStore> pharmacyStore = criteria.list();
        if (null == pharmacyStore || pharmacyStore.isEmpty()) {
            return null;
        }
        return pharmacyStore.get(0);
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
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyInventoryByDrugUuid(org.openmrs.Drug,String)
     */

    public PharmacyStore getPharmacyInventoryByDrugUuid(Drug uuid,String location) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Expression.eq("drugs", uuid))
                .add(Expression.eq("location", location));

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

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyInventory(org.openmrs.module.pharmacy.model.PharmacyStore)
     */

    public DrugTransactions saveDrugTransactions(DrugTransactions drugTransactions) {
        sessionFactory.getCurrentSession().saveOrUpdate(drugTransactions);

        return drugTransactions;
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyInventory(org.openmrs.module.pharmacy.model.PharmacyStore)
     */

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
                .add(Expression.eq("voided", false));

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

    public DrugDispenseSettings getDrugDispenseSettingsByLocation(PharmacyLocations uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugDispenseSettings.class)
                .add(Expression.eq("location", uuid));

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



    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacySupplier(org.openmrs.module.pharmacy.model.PharmacySupplier)
     */

    public PharmacySupplier savePharmacySupplier(PharmacySupplier pharmacySupplier) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacySupplier);

        return pharmacySupplier;
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


    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyOrders()
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyDrugOrder> getPharmacyDrugOrders() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyDrugOrder.class)
                .add(Expression.eq("voided", false));

        return criteria.list();

    }

    /**
     */

    public PharmacyDrugOrder getPharmacyDrugOrdersByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyDrugOrder.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyDrugOrder> pharmacyDrugOrder = criteria.list();
        if (null == pharmacyDrugOrder || pharmacyDrugOrder.isEmpty()) {
            return null;
        }
        return pharmacyDrugOrder.get(0);
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

    public List<DrugExtra> getDrugRange(Date minDate, Date maxDate) {
        String sql = "SELECT * FROM pharmacy_drug_extra  WHERE dateCreated BETWEEN :sDate AND :eDate AND drug_id IS NOT NULL AND receipt IS NOT NULL GROUP BY drug_id";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(DrugExtra.class);
        query.setParameter("sDate", minDate);
        query.setParameter("eDate", maxDate);
        List results = query.list();
        //log.info(results);
        return results;

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
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugDispenseSettingsByUuid(String)
     */

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
}





    
    
    
    
    
    
    
    
    
    
    
    
    
	
	
