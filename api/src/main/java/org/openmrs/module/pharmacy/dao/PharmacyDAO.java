/**
 *
 */
package org.openmrs.module.pharmacy.dao;

import org.openmrs.Drug;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.module.pharmacy.model.*;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


/**
 * @author Ampath Developers
 */
public interface PharmacyDAO {



    /**
     * save LocationSetter
     *
     * @param doses to be saved
     * @return LocationSetter  object
     */

    public boolean setPharmacyLocation(String doses);

    /**
     * save LocationSetter
     *
     * @return LocationSetter  object
     */

    public boolean setPharmacyLocationNull();

    /**
     * @return dose object by uuid
     */

    public String getPharmacyLocation();


    /**
     * save PharmacyStoreIncoming
     *
     * @param pharmacySupplier to be saved
     * @return saved PharmacyStoreIncoming object
     */

    public PharmacyStoreIncoming savePharmacyStoreIncoming(PharmacyStoreIncoming pharmacySupplier);
    /**
     * save PharmacyStoreIncoming
     *
     * @param pharmacySupplier to be saved
     * @return saved PharmacyStoreIncoming object
     */

    public boolean savePharmacyStoreIncoming(List<PharmacyStoreIncoming> pharmacySupplier);
    public DrugDispenseSettings getDrugDispenseSettingsByDrugIdAndLocation(Integer id,String locationUUID);
    /**
     * @return all the PharmacyStoreIncoming
     */
    public List<PharmacyStoreIncoming> getPharmacyStoreIncoming();

    /**
     * @return PharmacyStoreIncoming object by uuid
     */

    public PharmacyStoreIncoming getPharmacyStoreIncomingByUuid(String uuid);



    /**
     * save PharmacyStoreOutgoing
     *
     * @param pharmacySupplier to be saved
     * @return saved PharmacyStoreOutgoing object
     */

    public PharmacyStoreOutgoing savePharmacyStoreOutgoing(PharmacyStoreOutgoing pharmacySupplier);
    /**
     * save PharmacyStoreOutgoing
     *
     * @param pharmacySupplier to be saved
     * @return saved PharmacyStoreOutgoing object
     */

    public boolean savePharmacyStoreOutgoing(List<PharmacyStoreOutgoing> pharmacySupplier);

    /**
     * @return all the PharmacyStoreOutgoing
     */
    public List<PharmacyStoreOutgoing> getPharmacyStoreOutgoing();

    /**
     * @return PharmacyStoreOutgoing object by uuid
     */

    public PharmacyStoreOutgoing getPharmacyStoreOutgoingByUuid(String uuid);

    /**
     * save PharmacyStoreApproved
     *
     * @param pharmacySupplier to be saved
     * @return saved PharmacyStoreApproved object
     */

    public PharmacyStoreApproved savePharmacyStoreApproved(PharmacyStoreApproved pharmacySupplier);
    /**
     * save PharmacyStoreApproved
     *
     * @param pharmacySupplier to be saved
     * @return saved PharmacyStoreApproved object
     */

    public boolean savePharmacyStoreApproved(List<PharmacyStoreApproved> pharmacySupplier);

    /**
     * @return all the PharmacyStoreApproved
     */
    public List<PharmacyStoreApproved> getPharmacyStoreApproved();

    /**
     * @return PharmacyStoreApproved object by uuid
     */

    public PharmacyStoreApproved getPharmacyStoreApprovedByUuid(String uuid);

    public List<PharmacyStoreApproved> getPharmacyStoreApprovedByLocation(PharmacyLocations uuid);

    public List<PharmacyStoreIncoming> getPharmacyStoreIncomingByLocation(PharmacyLocations uuid);

    public List<PharmacyStoreOutgoing> getPharmacyStoreOutgoingByLocation(PharmacyLocations uuid);
    public List<DrugExtra> getUnprocessedReceiptsByEncounterUUID(String encounterUUID);

    /**
     * save DrugDispenseSettings
     *
     * @param drugDispenseSettings to be saved
     * @return saved DrugDispenseSettings object
     */



    public DrugDispenseSettings saveDrugDispenseSettings(DrugDispenseSettings drugDispenseSettings);

    /**
     * @return all the DrugDispenseSettings
     */
    public List<DrugDispenseSettings> getDrugDispenseSettings();

    /**
     * @return DrugDispenseSettings object by uuid
     */

    public DrugDispenseSettings getDrugDispenseSettingsByUuid(String uuid);

    /**
     * @return DrugDispenseSettings object by uuid
     */

    public DrugDispenseSettings getDrugDispenseSettingsByBatch(int name);

    /**
     * @return DrugDispenseSettings object by drug id
     */

    public DrugDispenseSettings getDrugDispenseSettingsByDrugId(Drug name);
    /**
     * @return DrugDispenseSettings object by drug id
     * @param name
     */

    public DrugDispenseSettings getDrugDispenseSettingsByLocation(String name);



    /**
     * save PharmacySupplier
     *
     * @param pharmacySupplier to be saved
     * @return saved PharmacySupplier object
     */

    public PharmacySupplier savePharmacySupplier(PharmacySupplier pharmacySupplier);
    public PharmacyDose savePharmacyDose(PharmacyDose pharmacyDose);
    /**
     * @return all the PharmacySupplier
     */
    public List<PharmacySupplier> getPharmacySupplier();

    /**
     * @return PharmacySupplier object by uuid
     */

    public PharmacySupplier getPharmacySupplierByUuid(String uuid);

    /**
     * @return DrugFormulation object by uuid
     */

    public PharmacySupplier getPharmacySupplierByName(String name);
    public List<PharmacyDose> getPharmacyDose();
    public PharmacyDose getPharmacyDoseByUuid(String uuid);
    public PharmacyDose getPharmacyDoseByName(String name);
    /**
     * save PharmacyCategory
     *
     * @param pharmacyCategory to be saved
     * @return saved PharmacyCategory object
     */

    public PharmacyCategory savePharmacyCategory(PharmacyCategory pharmacyCategory);

    /**
     * @return all the PharmacyCategory
     */
    public List<PharmacyCategory> getPharmacyCategory();

    /**
     * @return PharmacyCategory object by uuid
     */

    public PharmacyCategory getPharmacyCategoryByUuid(String uuid);

    /**
     * @return PharmacyCategory object by name
     */

    public PharmacyCategory getPharmacyCategoryByName(String name);


    /**
     * save PharmacyGeneralVariables
     *
     * @param pharmacyGeneralVariables to be saved
     * @return saved PharmacyGeneralVariables object
     */

    public PharmacyGeneralVariables savePharmacyGeneralVariables(PharmacyGeneralVariables pharmacyGeneralVariables);

    /**
     * @return all the PharmacyGeneralVariables
     */
    public List<PharmacyGeneralVariables> getPharmacyGeneralVariables();

    /**
     * @return PharmacyGeneralVariables object by uuid
     */

    public PharmacyGeneralVariables getPharmacyGeneralVariablesByUuid(String uuid);

    /**
     * @return PharmacyGeneralVariables object by name
     */

    public PharmacyGeneralVariables getPharmacyGeneralVariablesByName(String name);


    /**
     * save PharmacyTransactionTypes
     *
     * @param pharmacyTransactionType to be saved
     * @return saved PharmacyTransactionTypes object
     */

    public PharmacyTransactionTypes savePharmacyTransactionTypes(PharmacyTransactionTypes pharmacyTransactionType);

    /**
     * @return all the PharmacyTransactionType
     */
    public List<PharmacyTransactionTypes> getPharmacyTransactionTypes();

    /**
     * @return PharmacyTransactionTypes object by uuid
     */

    public PharmacyTransactionTypes getPharmacyTransactionTypesByUuid(String uuid);

    /**
     * @return PharmacyTransactionTypes object by uuid
     */

    public PharmacyTransactionTypes getPharmacyTransactionTypesByName(String name);

    /**
     * save PharmacyLocations
     *
     * @param pharmacyLocations to be saved
     * @return saved PharmacyLocations object
     */

    public PharmacyLocations savePharmacyLocations(PharmacyLocations pharmacyLocations);

    /**
     * @return all the PharmacyLocations
     */
    public List<PharmacyLocations> getPharmacyLocations();

    /**
     * @return PharmacyLocations object by uuid
     */

    public PharmacyLocations getPharmacyLocationsByUuid(String uuid);

    /**
     * @return PharmacyLocations object by uuid
     */

    public PharmacyLocations getPharmacyLocationsByName(String name);


    /**
     * save PharmacyLocationUsers
     *
     * @param pharmacyLocationUsers to be saved
     * @return saved PharmacyLocationUsers object
     */

    public PharmacyLocationUsers savePharmacyLocationUsers(PharmacyLocationUsers pharmacyLocationUsers);

    /**
     * @return all the PharmacyLocationUsers
     */
    public List<PharmacyLocationUsers> getPharmacyLocationUsers();

    /**
     * @return PharmacyLocationUsers object by uuid
     */

    public PharmacyLocationUsers getPharmacyLocationUsersByUuid(String uuid);


    /**
     * @return PharmacyLocationUsers object by uuid
     */

    public List<PharmacyLocationUsers> getPharmacyLocationUsersByUserName(String name);

    /**
     * save DrugMaxMin
     *
     * @param drugMaxMin to be saved
     * @return saved DrugMaxMin object
     */

    public DrugMaxMin saveDrugMaxMin(DrugMaxMin drugMaxMin);

    /**
     * @return all the DrugMaxMin
     */
    public List<DrugMaxMin> getDrugMaxMin();

    /**
     * @return PharmacyLocations object by uuid
     */

    public DrugMaxMin getDrugMaxMinByUuid(String uuid);

    /**
     * @return DrugMaxMin object by uuid
     */

    public DrugMaxMin getDrugMaxMinByDrug(Drug name);





    /**
     * save Indicators
     *
     * @param indicators to be saved
     * @return saved indicators object
     */

    public Indicators saveIndicators(Indicators indicators);

    /**
     * @return all the inidicators
     */
    public List<Indicators> getIndicators();

    /**
     * @return one indicators object by uuid
     */
    public Indicators getIndicatorsByUuid(String uuid);

    /**
     * @return list indicators object by uuid
     */
    public List<Indicators> getIndicatorsListByUuid(String one);

    /**
     * save pharmacyEncounter
     *
     * @param pharmacyEncounter to be saved
     * @return saved pharmacyEncounter object
     */

    public PharmacyEncounter savePharmacyEncounter(PharmacyEncounter pharmacyEncounter);
    public DrugExtra saveDrugExtraObject(DrugExtra drugExtra);
    /**
     * @return all the pharmacyEncounter
     */
    public List<PharmacyEncounter> getPharmacyEncounter();

    /**
     * @return one pharmacyEncounter object by uuid
     *
     */
    public PharmacyEncounter getPharmacyEncounterByUuid(String uuid);
    public Integer  getDrugsDispensedWithinPeriodRange(Date startDate,Date endDate, Integer drugID,String locationUUID);

    /**
     * @return list pharmacyEncounter object by uuid
     */
    public List<PharmacyEncounter> getPharmacyEncounterListByUuid(String uuid);


    public List<PharmacyEncounter> getPharmacyEncounterListByPatientId(Person id);
    /**
     * save pharmacyEncounterType
     *
     * @param pharmacyEncounterType to be saved
     * @return saved pharmacyEncounter object
     */

    public PharmacyEncounterType savePharmacyEncounterType(PharmacyEncounterType pharmacyEncounterType);
    public Integer getAmountWaivedWithinPeriodRange(Date startDate, Date endDate, Integer drugID, String locationUUID);
    public Integer  getNumberOfTimesDrugWaivedWithinPeriodRange(Date startDate,Date endDate, Integer drugID,String locationUUID);
    /**
     * @return all the pharmacyEncounterType
     */
    public List<PharmacyEncounterType> getPharmacyEncounterType();

    /**
     * @return one pharmacyEncounterType object by uuid
     */
    public PharmacyEncounterType getPharmacyEncounterTypeByUuid(String uuid);
    /**
     * @return one pharmacyEncounterType object by uuid
     */
    public PharmacyEncounterType getPharmacyEncounterTypeByName(String uuid);

    /**
     * @return list pharmacyEncounter object by uuid
     */
    public List<PharmacyEncounterType> getPharmacyEncounterTypeListByUuid(String uuid);

    /**
     * save pharmacyInventory
     *
     * @param pharmacyStore to be saved
     * @return saved pharmacyInventory object
     */

    public PharmacyStore savePharmacyInventory(PharmacyStore pharmacyStore);
    /**
     * save pharmacyInventory
     *
     * @param pharmacyStore to be saved
     * @return saved pharmacyInventory object
     */

    public boolean savePharmacyInventory(List<PharmacyStore> pharmacyStore);

    /**
     * @return all the pharmacyInventory
     */
    public List<PharmacyStore> getPharmacyInventory();

    /**
     * @return one pharmacyInventory object by uuid
     */
    public PharmacyStore getPharmacyInventoryByUuid(String uuid);

    /* @return one pharmacyInventory object by Category
    */
    public List<PharmacyStore> getPharmacyInventoryByCategory(PharmacyCategory uuid);
    public List<Drug> getPharmacyInventoryByNameAndLocation(String name,String location);
    public List<PharmacyStore> getPharmacyStoreByNameAndLocation(String name,String location);
    /**
     * @return list pharmacyInventory object by uuid
     */
    public List<PharmacyStore> getPharmacyInventoryListByUuid(String uuid);
    /**
     * @return one pharmacyInventory object by uuid
     */
    public PharmacyStore getPharmacyInventoryByDrugUuid(String uuid,String location);

    /**
     * save drugTransactions
     *
     * @param drugTransactions to be saved
     * @return saved drugTransactions object
     */

    public DrugTransactions saveDrugTransactions(DrugTransactions drugTransactions);
    /**
     * save drugTransactions
     *
     * @param drugTransactions to be saved
     * @return saved drugTransactions object
     */

    public boolean saveDrugTransactions(List<DrugTransactions> drugTransactions);

    /**
     * @return all the DrugTransaction
     */
    public List<DrugTransactions> getDrugTransactions();

    /**
     * @return one DrugTransaction object by uuid
     */
    public DrugTransactions getDrugTransactionsByUuid(String uuid);

    /**
     * @return list DrugTransactions object by uuid
     */
    public List<DrugTransactions> getDrugTransactionsListByUuid(String uuid);



    /**
     * save pharmacyExtra
     *
     * @param pharmacyObs to be saved
     * @return saved pharmacyExtra object
     */

    public boolean savePharmacyExtra(List<PharmacyExtra> pharmacyObs);

    /**
     * @return all the pharmacyExtra
     */
    public List<PharmacyExtra> getPharmacyExtra();

    /**
     * @return one pharmacyExtra object by uuid
     */
    public PharmacyExtra getPharmacyExtraByUuid(String uuid);


    /**
     * save pharmacyObs
     *
     * @param pharmacyObs to be saved
     * @return saved phamacyObs object
     */

    public boolean savePharmacyObs(List<PharmacyObs> pharmacyObs);

    /**
     * @return all the PharmacyObs
     */
    public List<PharmacyObs> getPharmacyObs();

    /**
     * @return one pharmacyInventory object by uuid
     */
    public PharmacyObs getPharmacyObsByUuid(String uuid);

    /**
     * @return one pharmacyInventory object by uuid
     */
    public List<PharmacyObs> getPharmacyObsByPharmacyOrder(PharmacyOrders uuid);





    /**
     * @return one pharmacyInventory object by uuid
     */
    public List<PharmacyObs> getPharmacyObsByEncounterId(PharmacyEncounter uuid);

    /**
     * save pharmacyOrders
     *
     * @param pharmacyOrders to be saved
     * @return saved pharmacyOrders object
     */

    public boolean savePharmacyOrders(List<PharmacyOrders>   pharmacyOrders);

    /**
     * @return all the pharmacyOrders
     */
    public List<PharmacyOrders> getPharmacyOrders();

    /**
     * @return one pharmacyOrders object by uuid
     */
    public PharmacyOrders getPharmacyOrdersByUuid(String uuid);

    /**
     * @return one pharmacyOrders object by  encounter uuid
     */
    public  List<PharmacyOrders> getPharmacyOrdersByEncounterId(PharmacyEncounter uuid);

    /**
     * save pharmacyDrugOrders
     *
     * @param pharmacyDrugOrders to be saved
     * @return saved pharmacyDrugOrders object
     */

    public boolean savePharmacyDrugOrders(List<PharmacyDrugOrder>   pharmacyDrugOrders);

    /**
     * @return all the pharmacyDrugOrders
     */
    public List<PharmacyDrugOrder> getPharmacyDrugOrders();

    /**
     * @return one pharmacyDrugOrders object by uuid
     */
    public PharmacyDrugOrder getPharmacyDrugOrdersByUuid(String uuid);

    public PharmacyDrugOrder getPharmacyDrugOrdersByOrders(PharmacyOrders uuid);


    /**
     * save pharmacyDrugOrderExtra
     *
     * @param pharmacyDrugOrderExtra to be saved
     * @return saved pharmacyDrugOrderExtra object
     */

    public boolean savePharmacyDrugOrderExtra(PharmacyDrugOrderExtra   pharmacyDrugOrderExtra);

    /**
     * @return all the pharmacyDrugOrders
     */
    public List<PharmacyDrugOrderExtra> getPharmacyDrugOrderExtra();

    /**
     * @return one pharmacyDrugOrderExtra object by uuid
     */
    public PharmacyDrugOrderExtra getPharmacyDrugOrderExtraByUuid(String uuid);


    /**
     * save drugExtra
     *
     * @param drugExtra to be saved
     * @return saved drugExtra object
     */

    public DrugExtra saveDrugExtra(List<DrugExtra> drugExtra);

    /**
     * @return all the DrugDispenseSettings
     */
    @Transactional(readOnly=true)
    public List<DrugExtra> getDrugExtra();

    /**
     * @return all the DrugDispenseSettings
     */
    @Transactional(readOnly=true)
    public List<DrugExtra> getDrugExtraRange(Date from, Date to);
    /**
     * @return all the DrugDispenseSettings
     */
    @Transactional(readOnly=true)
    public List<DrugExtra> getDrugRange(Date minDate, Date maxDate,String locationUUID);
    public List<PharmacyStoreIncoming> getDrugQuantityAfterLastStockTake(Date minDate, Date maxDate,String uuid);

    /**
     * @return all the DrugDispenseSettings
     */
    @Transactional(readOnly=true)
    public List<PharmacyEncounter> getEncountersRange(Date from, Date to,String location);
    public PharmacyEncounter  getCurrentPatientRegimen(String patientUUID);
    public Integer getNumberOfPatientsOnRegimen(Date startDate,Date endDate,String regimenCode);
    public String  getPatientByIdentifier(String identifier);
    /**
     * @return DrugDispenseSettings object by uuid
     */

    public DrugExtra getDrugExtraByUuid(String uuid);

    /**
     * @return DrugDispenseSettings object by uuid
     */
    @Transactional(readOnly=true)
    public DrugExtra getDrugExtraByBatch(int id);

    public PharmacyStore getBatchNoByNo(int batchno);
    public PharmacyTemporaryInventory saveTemporaryInventory(PharmacyTemporaryInventory pharmacyTemporaryInventory);
    public List<PharmacyEncounter> getPharmacyEncounterListByLocationUUID(String locationUUID);
    public List<PharmacyStore> getPharmacyStoreByLocation(String locationUUID);
    public PharmacyOrders getPharmacyOrderByEncounter(PharmacyEncounter uuid);
    public PharmacyObs getPharmacyObservationByPharmacyOrder(PharmacyOrders uuid);
    public PharmacyDrugOrder getPharmacyDrugOrdersByDrugExtraUUID(DrugExtra uuid);
    public Double  getDiscountOnDrugsWithinPeriodRange(Date startDate,Date endDate,String encounterUUID,String location);
    public PharmacyEncounter getLastPharmacyEncounterByPatientUUID(Patient patientUUID);
    public Double getDrugTotalCashCollectedWithinPeriodRange(Date startDate,Date endDate, Integer drugID,String locationUUID);
    public List<DrugExtra> getDrugExtraByPharmacyEncounter(PharmacyEncounter encounter);
    public List<PharmacyEncounter> getUnclearedPharmacyEncountersListByPersonID(Integer personID);
    public List<PharmacyStore> getInventoryByLocation(String locationUUID);
    public PharmacyStore getPharmacyStoreByDrugName(String locationUUID,Drug drug);
    public List<PharmacyEncounter> getDetailedEncountersRange(Date minDate,Date maxDate,String location);
    public List<S11> getS11WithinDateRange(PharmacyLocations location, Date startDate, Date endDate);
    public boolean savePharmacyS11(S11 pharmacyS11);
    public List<DrugTransactions>  getPharmacyDrugTransactionsByS11NO(String s11No,PharmacyLocations pharmacyLocation);
    public boolean saveGeneratedInventoryQuantities(List<GeneratePharmacyInventoryQuantities> stockQuantities);
    public GeneratePharmacyInventoryQuantities getDrugInventoryOpeningStockByDateAndLocation(Drug drug,Date startDate,Date endDate,String locationUUID);

}
