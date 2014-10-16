package org.openmrs.module.pharmacy.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Drug;
import org.openmrs.Person;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmacy.dao.PharmacyDAO;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;


import java.util.Date;
import java.util.List;

/**
 * @author Ampath developers
 */
public class PharmacyServiceImpl extends BaseOpenmrsService implements PharmacyService {

    protected static final Log log = LogFactory.getLog(PharmacyServiceImpl.class);

    private PharmacyDAO pharmacyDAO;

    public void setPharmacyDAO(PharmacyDAO pharmacyDAO) {
        this.pharmacyDAO = pharmacyDAO;
    }








    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveIndicators(org.openmrs.module.pharmacy.model.Indicators)
     */

    public Indicators saveIndicators(Indicators indicators) {
        return pharmacyDAO.saveIndicators(indicators);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getIndicators()
     */

    public List<Indicators> getIndicators() {
        return pharmacyDAO.getIndicators();
    }
    public DrugDispenseSettings getDrugDispenseSettingsByDrugIdAndLocation(Integer id,String locationUUID){
        return pharmacyDAO.getDrugDispenseSettingsByDrugIdAndLocation(id,locationUUID);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getIndicatorsByUuid(java.lang.String)
     */

    public Indicators getIndicatorsByUuid(String uuid) {
        return pharmacyDAO.getIndicatorsByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getIndicatorsListByUuid(java.lang.String)
     */

    public List<Indicators> getIndicatorsListByUuid(String one) {
        return pharmacyDAO.getIndicatorsListByUuid(one);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyEncounter(org.openmrs.module.pharmacy.model.PharmacyEncounter)
     */

    public PharmacyEncounter savePharmacyEncounter(PharmacyEncounter pharmacyEncounter) {
        return pharmacyDAO.savePharmacyEncounter(pharmacyEncounter);
    }
     public DrugExtra saveDrugExtraObject(DrugExtra drugExtra){
         return pharmacyDAO.saveDrugExtraObject(drugExtra);
     }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyEncounter()
     */

    public List<PharmacyEncounter> getPharmacyEncounter() {
        return pharmacyDAO.getPharmacyEncounter();
    }
    public List<DrugExtra> getUnprocessedReceiptsByEncounterUUID(String encounterUUID){
        return pharmacyDAO.getUnprocessedReceiptsByEncounterUUID(encounterUUID);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyEncounterByUuid(java.lang.String)
     */

    public PharmacyEncounter getPharmacyEncounterByUuid(String uuid) {
        return pharmacyDAO.getPharmacyEncounterByUuid(uuid);
    }
    public Integer  getDrugsDispensedWithinPeriodRange(Date startDate,Date endDate, Integer drugID,String locationUUID){
        return pharmacyDAO.getDrugsDispensedWithinPeriodRange(startDate, endDate, drugID, locationUUID);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyEncounterListByUuid(java.lang.String)
     */

    public List<PharmacyEncounter> getPharmacyEncounterListByUuid(String uuid) {
        return pharmacyDAO.getPharmacyEncounterListByUuid(uuid);

    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyEncounterListByUuid(java.lang.String)
     */

    public List<PharmacyEncounter> getPharmacyEncounterListByPatientId(Person id) {
        return pharmacyDAO.getPharmacyEncounterListByPatientId(id);

    }
    public Integer  getAmountWaivedWithinPeriodRange(Date startDate,Date endDate, Integer drugID,String locationUUID){
        return pharmacyDAO.getAmountWaivedWithinPeriodRange(startDate,endDate,drugID,locationUUID);
    }
    public Integer  getNumberOfTimesDrugWaivedWithinPeriodRange(Date startDate,Date endDate, Integer drugID,String locationUUID){
        return pharmacyDAO.getNumberOfTimesDrugWaivedWithinPeriodRange(startDate,endDate,drugID,locationUUID);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyEncounterType(org.openmrs.module.pharmacy.model.PharmacyEncounterType)
     */

    public PharmacyEncounterType savePharmacyEncounterType(PharmacyEncounterType pharmacyEncounterType) {
        return pharmacyDAO.savePharmacyEncounterType(pharmacyEncounterType);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyEncounterType()
     */

    public List<PharmacyEncounterType> getPharmacyEncounterType() {
        return pharmacyDAO.getPharmacyEncounterType();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyEncounterTypeByUuid(java.lang.String)
     */

    public PharmacyEncounterType getPharmacyEncounterTypeByUuid(String uuid) {
        return pharmacyDAO.getPharmacyEncounterTypeByUuid(uuid);
    }


    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyEncounterTypeByName(java.lang.String)
     */

    public PharmacyEncounterType getPharmacyEncounterTypeByName(String uuid) {
        return pharmacyDAO.getPharmacyEncounterTypeByName(uuid);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyEncounterTypeListByUuid(java.lang.String)
     */

    public List<PharmacyEncounterType> getPharmacyEncounterTypeListByUuid(String uuid) {
        return pharmacyDAO.getPharmacyEncounterTypeListByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyInventory(org.openmrs.module.pharmacy.model.PharmacyStore)
     */

    public PharmacyStore savePharmacyInventory(PharmacyStore pharmacyStore) {
        return pharmacyDAO.savePharmacyInventory(pharmacyStore);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyInventory(org.openmrs.module.pharmacy.model.PharmacyStore)
     */

    public boolean savePharmacyInventory(List<PharmacyStore> pharmacyStore) {
        return pharmacyDAO.savePharmacyInventory(pharmacyStore);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyInventory()
     */

    public List<PharmacyStore> getPharmacyInventory() {
        return pharmacyDAO.getPharmacyInventory();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyInventoryByUuid(java.lang.String)
     */

    public PharmacyStore getPharmacyInventoryByUuid(String uuid) {
        return pharmacyDAO.getPharmacyInventoryByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyInventoryByCategory(PharmacyCategory)
     */

    public List<PharmacyStore> getPharmacyInventoryByCategory(PharmacyCategory uuid) {
        return pharmacyDAO.getPharmacyInventoryByCategory(uuid);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyInventoryByDrugUuid(String, String)      */

    public PharmacyStore getPharmacyInventoryByDrugUuid(String uuid,String location) {
        return pharmacyDAO.getPharmacyInventoryByDrugUuid(uuid,location);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyInventoryListByUuid(java.lang.String)
     */

    public List<PharmacyStore> getPharmacyInventoryListByUuid(String uuid) {
        return pharmacyDAO.getPharmacyInventoryListByUuid(uuid);
    }
    public List<PharmacyStore> getPharmacyInventoryByNameAndLocation(String name,String location){
        return pharmacyDAO.getPharmacyInventoryByNameAndLocation(name,location);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveDrugTransactions(org.openmrs.module.pharmacy.model.DrugTransactions)
     */

    public DrugTransactions saveDrugTransactions(DrugTransactions drugTransactions) {
        return pharmacyDAO.saveDrugTransactions(drugTransactions);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveDrugTransactions(org.openmrs.module.pharmacy.model.DrugTransactions)
     */

    public boolean saveDrugTransactions(List<DrugTransactions> drugTransactions) {
        return pharmacyDAO.saveDrugTransactions(drugTransactions);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugTransactions()
     */

    public List<DrugTransactions> getDrugTransactions() {
        return pharmacyDAO.getDrugTransactions();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugTransactionsByUuid(java.lang.String)
     */

    public DrugTransactions getDrugTransactionsByUuid(String uuid) {
        return pharmacyDAO.getDrugTransactionsByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugTransactionsListByUuid(java.lang.String)
     */

    public List<DrugTransactions> getDrugTransactionsListByUuid(String uuid) {
        return pharmacyDAO.getDrugTransactionsListByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyObs(List)
     */

    public boolean savePharmacyObs(List<PharmacyObs> pharmacyObs) {
        return pharmacyDAO.savePharmacyObs(pharmacyObs);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyObs()
     */

    public List<PharmacyObs> getPharmacyObs() {
        return pharmacyDAO.getPharmacyObs();

    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyExtra(List)
     */

    public boolean savePharmacyExtra(List<PharmacyExtra> pharmacyExtra) {
        return pharmacyDAO.savePharmacyExtra(pharmacyExtra);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyExtra()
     */

    public List<PharmacyExtra> getPharmacyExtra() {
        return pharmacyDAO.getPharmacyExtra();

    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyExtraByUuid(java.lang.String)
     */

    public PharmacyExtra getPharmacyExtraByUuid(String uuid) {
        return pharmacyDAO.getPharmacyExtraByUuid(uuid);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyObsByUuid(java.lang.String)
     */

    public PharmacyObs getPharmacyObsByUuid(String uuid) {
        return pharmacyDAO.getPharmacyObsByUuid(uuid);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyObsByUuid(java.lang.String)
     */

    public List<PharmacyObs> getPharmacyObsByPharmacyOrder(PharmacyOrders uuid) {
        return pharmacyDAO.getPharmacyObsByPharmacyOrder(uuid);
    }





    public List<PharmacyObs> getPharmacyObsByEncounterId(PharmacyEncounter id) {
        return pharmacyDAO.getPharmacyObsByEncounterId(id);

    }


    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyOrders(List)
     */

    public boolean savePharmacyOrders(List<PharmacyOrders> pharmacyOrders) {
        return pharmacyDAO.savePharmacyOrders(pharmacyOrders);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyOrders()
     */

    public List<PharmacyOrders> getPharmacyOrders() {
        return pharmacyDAO.getPharmacyOrders();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyOrdersByUuid(java.lang.String)
     */

    public PharmacyOrders getPharmacyOrdersByUuid(String uuid) {
        return pharmacyDAO.getPharmacyOrdersByUuid(uuid);
    }


    public List<PharmacyOrders> getPharmacyOrdersByEncounterId(PharmacyEncounter uuid) {
        return pharmacyDAO.getPharmacyOrdersByEncounterId(uuid);
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacySupplier(org.openmrs.module.pharmacy.model.PharmacySupplier)
     */
    public PharmacySupplier savePharmacySupplier(PharmacySupplier pharmacySupplier) {
        return pharmacyDAO.savePharmacySupplier(pharmacySupplier);
    }
    public PharmacyDose savePharmacyDose(PharmacyDose pharmacyDose) {
        return pharmacyDAO.savePharmacyDose(pharmacyDose);
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacySupplier()
     */
    public List<PharmacySupplier> getPharmacySupplier() {
        return pharmacyDAO.getPharmacySupplier();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacySupplierByUuid(java.lang.String)
     */
    public PharmacySupplier getPharmacySupplierByUuid(String uuid) {
        return pharmacyDAO.getPharmacySupplierByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacySupplierByName(java.lang.String)
     */
    public PharmacySupplier getPharmacySupplierByName(String name) {
        return pharmacyDAO.getPharmacySupplierByName(name);
    }
    public List<PharmacyDose> getPharmacyDose() {
        return pharmacyDAO.getPharmacyDose();
    }
    public PharmacyDose getPharmacyDoseByUuid(String uuid) {
        return pharmacyDAO.getPharmacyDoseByUuid(uuid);
    }
    public PharmacyDose getPharmacyDoseByName(String name) {
        return pharmacyDAO.getPharmacyDoseByName(name);
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyTransactionTypes(org.openmrs.module.pharmacy.model.PharmacyTransactionTypes)
     */
    public PharmacyTransactionTypes savePharmacyTransactionTypes(PharmacyTransactionTypes pharmacyTransactionType) {
        return pharmacyDAO.savePharmacyTransactionTypes(pharmacyTransactionType);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyTransactionTypes()
     */
    public List<PharmacyTransactionTypes> getPharmacyTransactionTypes() {
        return pharmacyDAO.getPharmacyTransactionTypes();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyTransactionTypesByUuid(java.lang.String)
     */
    public PharmacyTransactionTypes getPharmacyTransactionTypesByUuid(String uuid) {
        return pharmacyDAO.getPharmacyTransactionTypesByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyTransactionTypesByName(java.lang.String)
     */

    public PharmacyTransactionTypes getPharmacyTransactionTypesByName(String name) {
        return pharmacyDAO.getPharmacyTransactionTypesByName(name);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyStoreIncoming(org.openmrs.module.pharmacy.model.PharmacyStoreIncoming)
     */
    public PharmacyStoreIncoming savePharmacyStoreIncoming(PharmacyStoreIncoming pharmacySupplier) {
        return pharmacyDAO.savePharmacyStoreIncoming(pharmacySupplier);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyStoreIncoming(org.openmrs.module.pharmacy.model.PharmacyStoreIncoming)
     */
    public boolean savePharmacyStoreIncoming(List<PharmacyStoreIncoming> pharmacySupplier) {

        return pharmacyDAO.savePharmacyStoreIncoming(pharmacySupplier);

    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyStoreIncoming()
     */
    public List<PharmacyStoreIncoming> getPharmacyStoreIncoming() {
        return pharmacyDAO.getPharmacyStoreIncoming();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyStoreIncomingByUuid(java.lang.String)
     */
    public PharmacyStoreIncoming getPharmacyStoreIncomingByUuid(String uuid) {
        return pharmacyDAO.getPharmacyStoreIncomingByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyStoreOutgoing(org.openmrs.module.pharmacy.model.PharmacyStoreOutgoing)
     */
    public PharmacyStoreOutgoing savePharmacyStoreOutgoing(PharmacyStoreOutgoing savePharmacyStoreOutgoing) {
        return pharmacyDAO.savePharmacyStoreOutgoing(savePharmacyStoreOutgoing);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyStoreOutgoing(org.openmrs.module.pharmacy.model.PharmacyStoreOutgoing)
     */
    public boolean savePharmacyStoreOutgoing(List<PharmacyStoreOutgoing> savePharmacyStoreOutgoing) {

        return pharmacyDAO.savePharmacyStoreOutgoing(savePharmacyStoreOutgoing);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyStoreOutgoing()
     */
    public List<PharmacyStoreOutgoing> getPharmacyStoreOutgoing() {
        return pharmacyDAO.getPharmacyStoreOutgoing();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyStoreOutgoingByUuid(java.lang.String)
     */
    public PharmacyStoreOutgoing getPharmacyStoreOutgoingByUuid(String uuid) {
        return pharmacyDAO.getPharmacyStoreOutgoingByUuid(uuid);
    }




    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#setPharmacyLocation(java.lang.String)
     */
    public boolean setPharmacyLocation(String loc) {
        pharmacyDAO.setPharmacyLocation(loc);
        return true;
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#setPharmacyLocationNull()
     */
    public void setPharmacyLocationNull() {
        pharmacyDAO.setPharmacyLocationNull();

    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyLocation()
     */

    public String getPharmacyLocation() {
        return pharmacyDAO.getPharmacyLocation();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveDrugDispenseSettings(org.openmrs.module.pharmacy.model.DrugDispenseSettings)
     */
    public DrugDispenseSettings saveDrugDispenseSettings(DrugDispenseSettings drugDispenseSettings) {
        return pharmacyDAO.saveDrugDispenseSettings(drugDispenseSettings);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugDispenseSettings()
     */

    public List<DrugDispenseSettings> getDrugDispenseSettings() {
        return pharmacyDAO.getDrugDispenseSettings();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugDispenseSettingsByUuid(java.lang.String)
     */

    public DrugDispenseSettings getDrugDispenseSettingsByUuid(String uuid) {
        return pharmacyDAO.getDrugDispenseSettingsByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugDispenseSettingsByBatch(int)
     */

    public DrugDispenseSettings getDrugDispenseSettingsByBatch(int name) {
        return pharmacyDAO.getDrugDispenseSettingsByBatch(name);
    }

    public DrugExtra saveDrugExtra(List<DrugExtra> drugExtra) {
        return pharmacyDAO.saveDrugExtra(drugExtra) ;
    }

    public List<DrugExtra> getDrugExtra() {
        return pharmacyDAO.getDrugExtra();
    }
    public List<DrugExtra> getDrugExtraRange(Date from, Date to) {
        return pharmacyDAO.getDrugExtraRange(from,to);
    }
    public List<DrugExtra> getDrugRange(Date from, Date to,String locationUUID){
        return pharmacyDAO.getDrugRange(from,to,locationUUID);
    }
    public List<PharmacyEncounter> getEncountersRange(Date from, Date to,String location) {
        return pharmacyDAO.getEncountersRange(from,to,location);
    }
    public List<PharmacyEncounter> getCurrentPatientRegimen(String patientUUID){
        return pharmacyDAO.getCurrentPatientRegimen(patientUUID);
    }
    public Integer getNumberOfPatientsOnRegimen(Date startDate,Date endDate,String regimenCode){
        return pharmacyDAO.getNumberOfPatientsOnRegimen(startDate,endDate,regimenCode);
    }
    public String  getPatientByIdentifier(String identifier){
        return pharmacyDAO.getPatientByIdentifier(identifier);
    }
    public List<PharmacyStoreIncoming> getDrugQuantityAfterLastStockTake(Date minDate, Date maxDate,String uuid){
        return pharmacyDAO.getDrugQuantityAfterLastStockTake(minDate,maxDate,uuid);
    }
    public DrugExtra getDrugExtraByUuid(String uuid) {
        return pharmacyDAO.getDrugExtraByUuid(uuid)  ;
    }

    public DrugExtra getDrugExtraByBatch(int id) {
        return pharmacyDAO.getDrugExtraByBatch(id);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugDispenseSettingsByDrugId(Drug)
     */

    public DrugDispenseSettings getDrugDispenseSettingsByDrugId(Drug id) {
        return pharmacyDAO.getDrugDispenseSettingsByDrugId(id);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugDispenseSettingsByLocation(String) (Drug)
     * @param id
     */

    public DrugDispenseSettings getDrugDispenseSettingsByLocation(String id) {
        return pharmacyDAO.getDrugDispenseSettingsByLocation(id);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyCategory(org.openmrs.module.pharmacy.model.PharmacyCategory)
     */

    public PharmacyCategory savePharmacyCategory(PharmacyCategory pharmacyCategory) {
        return pharmacyDAO.savePharmacyCategory(pharmacyCategory);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyCategory()
     */

    public List<PharmacyCategory> getPharmacyCategory() {
        return pharmacyDAO.getPharmacyCategory();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyCategoryByUuid(java.lang.String)
     */

    public PharmacyCategory getPharmacyCategoryByUuid(String uuid) {
        return pharmacyDAO.getPharmacyCategoryByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyCategoryByName(java.lang.String)
     */

    public PharmacyCategory getPharmacyCategoryByName(String name) {
        return pharmacyDAO.getPharmacyCategoryByName(name);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyGeneralVariables(org.openmrs.module.pharmacy.model.PharmacyGeneralVariables)
     */

    public PharmacyGeneralVariables savePharmacyGeneralVariables(PharmacyGeneralVariables pharmacyGeneralVariables) {
        return pharmacyDAO.savePharmacyGeneralVariables(pharmacyGeneralVariables);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyGeneralVariables()
     */

    public List<PharmacyGeneralVariables> getPharmacyGeneralVariables() {
        return pharmacyDAO.getPharmacyGeneralVariables();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyGeneralVariablesByUuid(java.lang.String)
     */

    public PharmacyGeneralVariables getPharmacyGeneralVariablesByUuid(String uuid) {
        return pharmacyDAO.getPharmacyGeneralVariablesByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyGeneralVariablesByName(java.lang.String)
     */

    public PharmacyGeneralVariables getPharmacyGeneralVariablesByName(String name) {
        return pharmacyDAO.getPharmacyGeneralVariablesByName(name);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyLocations(org.openmrs.module.pharmacy.model.PharmacyLocations)
     */

    public PharmacyLocations savePharmacyLocations(PharmacyLocations pharmacyLocations) {
        return pharmacyDAO.savePharmacyLocations(pharmacyLocations);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyLocations()
     */

    public List<PharmacyLocations> getPharmacyLocations() {
        return pharmacyDAO.getPharmacyLocations();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyLocationsByUuid(java.lang.String)
     */

    public PharmacyLocations getPharmacyLocationsByUuid(String uuid) {
        return pharmacyDAO.getPharmacyLocationsByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyLocationsByName(java.lang.String)
     */

    public PharmacyLocations getPharmacyLocationsByName(String name) {
        return pharmacyDAO.getPharmacyLocationsByName(name);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyLocationUsers(org.openmrs.module.pharmacy.model.PharmacyLocationUsers)
     */

    public PharmacyLocationUsers savePharmacyLocationUsers(PharmacyLocationUsers pharmacyLocationUsers) {
        return pharmacyDAO.savePharmacyLocationUsers(pharmacyLocationUsers);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyLocationUsers()
     */

    public List<PharmacyLocationUsers> getPharmacyLocationUsers() {
        return pharmacyDAO.getPharmacyLocationUsers();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyLocationUsersByUuid(java.lang.String)
     */

    public PharmacyLocationUsers getPharmacyLocationUsersByUuid(String uuid) {
        return pharmacyDAO.getPharmacyLocationUsersByUuid(uuid);
    }


    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyLocationUsersByUserName(java.lang.String)
     */

    public List<PharmacyLocationUsers> getPharmacyLocationUsersByUserName(String name) {
        return pharmacyDAO.getPharmacyLocationUsersByUserName(name);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveDrugMaxMin(org.openmrs.module.pharmacy.model.DrugMaxMin)
     */

    public DrugMaxMin saveDrugMaxMin(DrugMaxMin drugMaxMin) {
        return pharmacyDAO.saveDrugMaxMin(drugMaxMin);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugMaxMin()
     */

    public List<DrugMaxMin> getDrugMaxMin() {
        return pharmacyDAO.getDrugMaxMin();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugMaxMinByUuid(java.lang.String)
     */

    public DrugMaxMin getDrugMaxMinByUuid(String uuid) {
        return pharmacyDAO.getDrugMaxMinByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugMaxMinByDrug(org.openmrs.Drug)
     */

    public DrugMaxMin getDrugMaxMinByDrug(Drug name) {
        return pharmacyDAO.getDrugMaxMinByDrug(name);
    }

    public boolean savePharmacyDrugOrders(List<PharmacyDrugOrder> pharmacyDrugOrders) {
        return pharmacyDAO.savePharmacyDrugOrders(pharmacyDrugOrders);
    }

    public List<PharmacyDrugOrder> getPharmacyDrugOrders() {
        return pharmacyDAO.getPharmacyDrugOrders();
    }

    public PharmacyDrugOrder getPharmacyDrugOrdersByUuid(String uuid) {
        return pharmacyDAO.getPharmacyDrugOrdersByUuid(uuid);
    }

    public PharmacyDrugOrder getPharmacyDrugOrdersByOrders(PharmacyOrders uuid){

        return pharmacyDAO.getPharmacyDrugOrdersByOrders( uuid);

    }
    public boolean savePharmacyDrugOrderExtra(PharmacyDrugOrderExtra pharmacyDrugOrderExtra) {
        return pharmacyDAO.savePharmacyDrugOrderExtra(pharmacyDrugOrderExtra);
    }

    public List<PharmacyDrugOrderExtra> getPharmacyDrugOrderExtra() {
       return pharmacyDAO.getPharmacyDrugOrderExtra();
    }

    public PharmacyDrugOrderExtra getPharmacyDrugOrderExtraByUuid(String uuid) {
       return pharmacyDAO.getPharmacyDrugOrderExtraByUuid(uuid);
    }


    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyStoreApproved(org.openmrs.module.pharmacy.model.PharmacyStoreApproved)
     */

    public PharmacyStoreApproved savePharmacyStoreApproved(PharmacyStoreApproved savePharmacyStoreApproved) {
        return pharmacyDAO.savePharmacyStoreApproved(savePharmacyStoreApproved);
    }


    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyStoreApproved(org.openmrs.module.pharmacy.model.PharmacyStoreApproved)
     */

    public boolean savePharmacyStoreApproved(List<PharmacyStoreApproved> savePharmacyStoreApproved) {
        return pharmacyDAO.savePharmacyStoreApproved(savePharmacyStoreApproved);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyStoreApproved()
     */

    public List<PharmacyStoreApproved> getPharmacyStoreApproved() {
        return pharmacyDAO.getPharmacyStoreApproved();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyStoreApprovedByUuid(java.lang.String)
     */

    public PharmacyStoreApproved getPharmacyStoreApprovedByUuid(String uuid) {
        return pharmacyDAO.getPharmacyStoreApprovedByUuid(uuid);
    }
    public List<PharmacyStoreApproved> getPharmacyStoreApprovedByLocation(PharmacyLocations uuid) {
        return pharmacyDAO.getPharmacyStoreApprovedByLocation(uuid);
    }
    public List<PharmacyStoreIncoming> getPharmacyStoreIncomingByLocation(PharmacyLocations uuid) {
        return pharmacyDAO.getPharmacyStoreIncomingByLocation(uuid);
    }
    public List<PharmacyStoreOutgoing> getPharmacyStoreOutgoingByLocation(PharmacyLocations uuid) {
        return pharmacyDAO.getPharmacyStoreOutgoingByLocation(uuid);
    }
    public PharmacyStore getBatchNoByNo(int batchno){
        return pharmacyDAO.getBatchNoByNo(batchno);
    }
    public PharmacyTemporaryInventory saveTemporaryInventory(PharmacyTemporaryInventory pharmacyTemporaryInventory){
        return pharmacyDAO.saveTemporaryInventory(pharmacyTemporaryInventory);
    }
    public List<PharmacyEncounter> getPharmacyEncounterListByLocationUUID(String locationUUID){
        return pharmacyDAO.getPharmacyEncounterListByLocationUUID(locationUUID);
    }
    public List<PharmacyStore> getPharmacyStoreByLocation(String locationUUID){
        return pharmacyDAO.getPharmacyStoreByLocation(locationUUID);
    }
}
