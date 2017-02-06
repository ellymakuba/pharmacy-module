/**
 *
 */
package org.openmrs.module.pharmacy.service;

import org.openmrs.Drug;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.biometrics.model.PatientFingerPrintModel;
import org.openmrs.module.pharmacy.model.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 *
 *
 * @author Ampath Developers
 */
@Transactional
public interface PharmacyService extends OpenmrsService {




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
     *d
     * @return LocationSetter  null
     */
    public void setPharmacyLocationNull();

    /**
     * @return location object
     */


    @Transactional(readOnly=true)
    public String getPharmacyLocation();



    /* save PharmacyStoreApproved
      *
      * @param PharmacyStoreApproved to be saved
      * @return saved PharmacyStoreApproved object
      */

    public PharmacyStoreApproved savePharmacyStoreApproved(PharmacyStoreApproved pharmacySupplier);
    public DrugDispenseSettings getDrugDispenseSettingsByDrugIdAndLocation(Integer id,String locationUUID);
    /* save PharmacyStoreApproved
    *
    * @param PharmacyStoreApproved to be saved
    * @return saved PharmacyStoreApproved object
    */

    public boolean savePharmacyStoreApproved(List<PharmacyStoreApproved> pharmacySupplier);

    /**
     * @return all the PharmacyStoreApproved
     */
    @Transactional(readOnly=true)
    public List<PharmacyStoreApproved> getPharmacyStoreApproved();

    /**
     * @return PharmacyStoreApproved object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyStoreApproved getPharmacyStoreApprovedByUuid(String uuid);

    /**
     * @return PharmacyStoreApproved object by uuid
     */
    @Transactional(readOnly=true)
    public List<PharmacyStoreApproved> getPharmacyStoreApprovedByLocation(PharmacyLocations uuid);

    public List<PharmacyStoreIncoming> getPharmacyStoreIncomingByLocation(PharmacyLocations uuid);

    public List<PharmacyStoreOutgoing> getPharmacyStoreOutgoingByLocation(PharmacyLocations uuid);

    public List<DrugExtra> getUnprocessedReceiptsByEncounterUUID(String encounterUUID);
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
    @Transactional(readOnly=true)
    public List<PharmacyLocations> getPharmacyLocations();

    /**
     * @return PharmacyLocations object by uuid
     */

    @Transactional(readOnly=true)
    public PharmacyLocations getPharmacyLocationsByUuid(String uuid);

    /**
     * @return PharmacyLocations object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyLocations getPharmacyLocationsByName(String name);


    /**
     * save PharmacyLocations
     *
     * @param pharmacyLocationUsers to be saved
     * @return saved PharmacyLocations object
     */

    public PharmacyLocationUsers savePharmacyLocationUsers(PharmacyLocationUsers pharmacyLocationUsers);

    /**
     * @return all the PharmacyLocations
     */
    @Transactional(readOnly=true)
    public List<PharmacyLocationUsers> getPharmacyLocationUsers();

    /**
     * @return PharmacyLocationUsers object by uuid
     */

    @Transactional(readOnly=true)
    public PharmacyLocationUsers getPharmacyLocationUsersByUuid(String uuid);



    /**
     * @return PharmacyLocationUsers object by uuid
     */
    @Transactional(readOnly=true)
    public List<PharmacyLocationUsers> getPharmacyLocationUsersByUserName(String name);










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
    @Transactional(readOnly=true)
    public List<DrugTransactions> getDrugTransactions();

    /**
     * @return one DrugTransaction object by uuid
     */
    @Transactional(readOnly=true)
    public DrugTransactions getDrugTransactionsByUuid(String uuid);

    /**
     * @return list DrugTransactions object by uuid
     */
    @Transactional(readOnly=true)
    public List<DrugTransactions> getDrugTransactionsListByUuid(String uuid);



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
    @Transactional(readOnly=true)
    public List<Indicators> getIndicators();

    /**
     * @return one indicators object by uuid
     */
    @Transactional(readOnly=true)
    public Indicators getIndicatorsByUuid(String uuid);

    /**
     * @return list indicators object by uuid
     */
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public List<PharmacyEncounter> getPharmacyEncounter();

    /**
     * @return one pharmacyEncounter object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyEncounter getPharmacyEncounterByUuid(String uuid);
    public Integer  getDrugsDispensedWithinPeriodRange(Date startDate,Date endDate, Integer drugID,String locationUUID);

    /**
     * @return list pharmacyEncounter object by uuid
     */
    @Transactional(readOnly=true)
    public List<PharmacyEncounter> getPharmacyEncounterListByUuid(String uuid);

    @Transactional(readOnly=true)
    public List<PharmacyEncounter> getPharmacyEncounterListByPatientId(Person id);
    /**
     * save pharmacyEncounterType
     *
     * @param pharmacyEncounterType to be saved
     * @return saved pharmacyEncounter object
     */

    public PharmacyEncounterType savePharmacyEncounterType(PharmacyEncounterType pharmacyEncounterType);
    public Integer  getAmountWaivedWithinPeriodRange(Date startDate,Date endDate, Integer drugID,String locationUUID);
    public Integer  getNumberOfTimesDrugWaivedWithinPeriodRange(Date startDate,Date endDate, Integer drugID,String locationUUID);
    /**
     * @return all the pharmacyEncounterType
     */
    @Transactional(readOnly=true)
    public List<PharmacyEncounterType> getPharmacyEncounterType();

    /**
     * @return one pharmacyEncounterType object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyEncounterType getPharmacyEncounterTypeByUuid(String uuid);
    /**
     * @return one pharmacyEncounterType object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyEncounterType getPharmacyEncounterTypeByName(String uuid);

    /**
     * @return list pharmacyEncounter object by uuid
     */
    @Transactional(readOnly=true)
    public List<PharmacyEncounterType> getPharmacyEncounterTypeListByUuid(String uuid);

    /**
     * save pharmacyInventory
     *
     * @param pharmacyStore to be saved
     * @return saved pharmacyInventory object
     */

    public PharmacyStore savePharmacyInventoryItem(PharmacyStore pharmacyStore);
    /**
     * save pharmacyOrders
     *
     * @param pharmacyStore to be saved
     * @return saved PharmacyStore object
     */

    public boolean savePharmacyInventory(List<PharmacyStore> pharmacyStore);

    /**
     * @return all the pharmacyInventory
     */
    @Transactional(readOnly=true)
    public List<PharmacyStore> getPharmacyInventory();

    /**
     * @return one pharmacyInventory object by uuid
     */
    public PharmacyStore getPharmacyInventoryByUuid(String uuid);

    /**
     * @return one pharmacyInventory object by uuid
     */
    public List<PharmacyStore> getPharmacyInventoryByCategory(PharmacyCategory uuid);
    public List<Drug> getPharmacyInventoryByNameAndLocation(String name,PharmacyLocations location);
    public List<PharmacyStore> getPharmacyStoreByNameAndLocation(String name,PharmacyLocations location);
    /**
     * @return list pharmacyInventory object by uuid
     */
    @Transactional(readOnly=true)
    public List<PharmacyStore> getPharmacyInventoryListByUuid(String uuid);

    /**
     * save pharmacyExtra
     *
     * @param pharmacyObs to be saved
     * @return saved pharmacyExtra object
     */

    public boolean savePharmacyExtra(List<PharmacyExtra> pharmacyObs);

    /**
     * @return all the PharmacyExtra
     */
    @Transactional(readOnly=true)
    public List<PharmacyExtra> getPharmacyExtra();

    /**
     * @return one PharmacyExtra object by uuid
     */
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public List<PharmacyObs> getPharmacyObs();

    /**
     * @return one pharmacyInventory object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyObs getPharmacyObsByUuid(String uuid);

    /**
     * @return one pharmacyInventory object by uuid
     */
    @Transactional(readOnly=true)
    public List<PharmacyObs> getPharmacyObsByPharmacyOrder(PharmacyOrders uuid);


    /**
     * @return one pharmacyInventory object by EncounterId
     */
    @Transactional(readOnly=true)
    public List<PharmacyObs> getPharmacyObsByEncounterId(PharmacyEncounter id);
    /**
     * @return one pharmacyInventory object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyStore getPharmacyInventoryByDrugUuid(String uuid,PharmacyLocations location);

    /**
     * save pharmacyOrders
     *
     * @param pharmacyOrders to be saved
     * @return saved pharmacyOrders object
     */

    public boolean savePharmacyOrders(List<PharmacyOrders> pharmacyOrders);

    /**
     * @return all the pharmacyOrders
     */
    @Transactional(readOnly=true)
    public List<PharmacyOrders> getPharmacyOrders();

    /**
     * @return one pharmacyOrders object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyOrders getPharmacyOrdersByUuid(String uuid);

    /**
     * @return one pharmacyOrders object  by Encounter uuid
     */
    @Transactional(readOnly=true)
    public  List<PharmacyOrders> getPharmacyOrdersByEncounterId(PharmacyEncounter uuid);





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
    @Transactional(readOnly=true)
    public List<DrugDispenseSettings> getDrugDispenseSettings();

    /**
     * @return DrugDispenseSettings object by uuid
     */

    public DrugDispenseSettings getDrugDispenseSettingsByUuid(String uuid);

    /**
     * @return DrugDispenseSettings object by uuid
     */
    @Transactional(readOnly=true)
    public DrugDispenseSettings getDrugDispenseSettingsByBatch(int id);


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
    public List<PharmacyEncounter> getEncountersRange(Date f,Date t,String loc);
    public PharmacyEncounter  getCurrentPatientRegimen(String patientUUID);
    public Integer getNumberOfPatientsOnRegimen(Date startDate,Date endDate,String regimenCode,String locationUUID);
    public String  getPatientByIdentifier(String identifier);
    public List<PharmacyStoreIncoming> getDrugQuantityAfterLastStockTake(Date minDate, Date maxDate,String uuid);
    /**
     * @return DrugDispenseSettings object by uuid
     */

    public DrugExtra getDrugExtraByUuid(String uuid);

    /**
     * @return DrugDispenseSettings object by uuid
     */
    @Transactional(readOnly=true)
    public DrugExtra getDrugExtraByBatch(int id);



    /**
     * @return DrugDispenseSettings object by drug id
     */
    @Transactional(readOnly=true)
    public DrugDispenseSettings getDrugDispenseSettingsByDrugId(Drug id);

    /**
     * @return DrugDispenseSettings object by drug id
     * @param id
     */
    @Transactional(readOnly=true)
    public DrugDispenseSettings getDrugDispenseSettingsByLocation(String id);

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
    @Transactional(readOnly=true)
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

    /**
     * @return all the PharmacyStoreIncoming
     */
    @Transactional(readOnly=true)
    public List<PharmacyStoreIncoming> getPharmacyStoreIncoming();

    /**
     * @return PharmacyStoreIncoming object by uuid
     */
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public List<PharmacyStoreOutgoing> getPharmacyStoreOutgoing();

    /**
     * @return PharmacyStoreOutgoing object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyStoreOutgoing getPharmacyStoreOutgoingByUuid(String uuid);




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
    @Transactional(readOnly=true)
    public List<PharmacyTransactionTypes> getPharmacyTransactionTypes();

    /**
     * @return PharmacyTransactionTypes object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyTransactionTypes getPharmacyTransactionTypesByUuid(String uuid);

    /**
     * @return PharmacyTransactionTypes object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyTransactionTypes getPharmacyTransactionTypesByName(String name);

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
    @Transactional(readOnly=true)
    public List<PharmacyCategory> getPharmacyCategory();

    /**
     * @return PharmacyCategory object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyCategory getPharmacyCategoryByUuid(String uuid);

    /**
     * @return PharmacyCategory object by name
     */
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public List<PharmacyGeneralVariables> getPharmacyGeneralVariables();

    /**
     * @return PharmacyGeneralVariables object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyGeneralVariables getPharmacyGeneralVariablesByUuid(String uuid);

    /**
     * @return PharmacyGeneralVariables object by name
     */
    @Transactional(readOnly=true)
    public PharmacyGeneralVariables getPharmacyGeneralVariablesByName(String name);



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
    @Transactional(readOnly=true)
    public List<DrugMaxMin> getDrugMaxMin();

    /**
     * @return PharmacyLocations object by uuid
     */
    @Transactional(readOnly=true)
    public DrugMaxMin getDrugMaxMinByUuid(String uuid);

    /**
     * @return DrugMaxMin object by uuid
     */
    @Transactional(readOnly=true)
    public DrugMaxMin getDrugMaxMinByDrug(Drug name);

    /**
     * save pharmacyDrugOrders
     *
     * @param pharmacyDrugOrders to be saved
     * @return saved pharmacyDrugOrders object
     */

    public boolean savePharmacyDrugOrders(List<PharmacyDrugOrder>   pharmacyDrugOrders);

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
    public PharmacyStore getBatchNoByNo(int batchno);
    public PharmacyTemporaryInventory saveTemporaryInventory(PharmacyTemporaryInventory pharmacyTemporaryInventory);
    public List<PharmacyEncounter> getPharmacyEncounterListByLocationUUID(String locationUUID);
    public List<PharmacyStore> getPharmacyStoreByLocation(PharmacyLocations locationUUID);
    public PharmacyOrders getPharmacyOrderByEncounter(PharmacyEncounter uuid);
    public PharmacyObs getPharmacyObservationByPharmacyOrder(PharmacyOrders uuid);
    public PharmacyDrugOrder getPharmacyDrugOrdersByDrugExtraUUID(DrugExtra uuid);
    public Double  getDiscountOnDrugsWithinPeriodRange(Date startDate,Date endDate,String encounterUUID,String location);
    public PharmacyEncounter getLastPharmacyEncounterByPatientUUID(Patient patientUUID);
    public Double getDrugTotalCashCollectedWithinPeriodRange(Date startDate,Date endDate, Integer drugID,String locationUUID);
    public List<DrugExtra> getDrugExtraByPharmacyEncounter(PharmacyEncounter encounter);
    public List<PharmacyEncounter> getUnclearedPharmacyEncountersListByPersonID(Integer personID);
    public List<PharmacyStore> getInventoryByLocation(String locationUUID);
    public PharmacyStore getPharmacyStoreByDrugName(PharmacyLocations locationUUID,Drug drug);
    public List<PharmacyEncounter> getDetailedEncountersRange(Date minDate,Date maxDate,String location);
    public List<S11> getS11WithinDateRange(PharmacyLocations location, Date startDate, Date endDate);
    public boolean savePharmacyS11(S11 pharmacyS11);
    public List<DrugTransactions>  getPharmacyDrugTransactionsByS11NO(String s11No,PharmacyLocations pharmacyLocation);
    public boolean saveGeneratedInventoryQuantities(List<GeneratePharmacyInventoryQuantities> stockQuantities);
    public GeneratePharmacyInventoryQuantities getDrugInventoryOpeningStockByDateAndLocation(String drugUUID,Date startDate,Date endDate,String locationUUID);
    public List<PharmacyStore> getDrugTransactionsBetweenRange(Date startDate, Date endDate,String pharmacyLocation);
    public Integer computeQuantityOfDrugsReceivedWithinDateRange(Date startDate, Date endDate,PharmacyLocations pharmacyLocation,Drug drug);
    public Integer quantityOfDrugsIssuedThroughTranfersBetweenTwoDates(Date startDate, Date endDate,PharmacyLocations pharmacyLocation,Drug drug);
    public Integer computeQuantityOfDrugsDispensedWithinDateRange(Date startDate, Date endDate,String pharmacyLocation,String drug);
    public String getDrugTransactionByS11AndDrug(PharmacyLocations location, Drug drug,String s11);
    public DrugTransactions getDrugTransactionByLocationAndDrug(PharmacyLocations location, Drug drug, String transactionUUID);
    public List<DrugTransactions> getTransferedTransactionsdBetweenDates(Date startDate, Date endDate,String pharmacyLocationUUID);
    public Integer getDrugS11QuantityReceived(PharmacyLocations location, Drug drug,String s11);
    public List<S11> getDistinctS11WithinDateRange(String locationUUID, Date startDate, Date endDate);
    public PharmacyDose getPharmacyDoseByID(Integer ID);
    public List<DrugExtra> getWaiversWithinDateRange(String locUUID, Date startDate, Date endDate);
    public List<DrugExtra> getDiscountsWithinDateRange(String locUUID, Date startDate, Date endDate);
    public List<PharmacyStore> getAllPharmacyStorePlusRetired(PharmacyLocations locationUUID);
    public PharmacyDrugOrder getHIVPatientLastVisitPharmacyDrugOrder(String patientID,String formName);
    public List<DrugExtra> getDrugExtraRangeByDrugAndLocation(String pharmacyLocations,String drug,Date startDate,Date endDate);
    public InventoryMetaData savePharmacyInventoryMetaData(InventoryMetaData inventoryMetaData);
    public InventoryMetaData getInventoryMetaDataByDrugName(Drug drugName,PharmacyLocations pharmacyLocation);
    public InventoryMetaData getInventoryMetaDataByUUID(String UUID);
    public List<PharmacyStore> getPharmacyStoreForApprovalByLocation(PharmacyLocations locationUUID);
    public List<PharmacyEncounter> getEncountersRangeByRegimenName(Date minDate,Date maxDate,String location);
    public List<PharmacyEncounter> getCountOfPatientsOnRegimen(String regimen,Date minDate,Date maxDate,String location);
    public List<PharmacyDrugOrder> pharmacyDrugOrdersBetweenTwoDates(Date startDate, Date endDate,PharmacyLocations pharmacyLocation,Drug drug);
    public List<PharmacyEncounter> getPharmacyEncounterListByUserBetweenTwoDate(Date startDate,Date endDate,User user,PharmacyLocations pharmacyLocation);
    public PharmacyLocationUsers getPharmacyLocationUserByAmrsUserNameAndLocation(String amrsUser,String pharmacyLocationName);
    public Integer getCumilativeS11ReceivedForDrugAtLocationBetweenTwoDates(Date startDate,Date endDate,String drugUUID,String pharmacyLocationUUID);
    public Integer getCumilativeDrugStockTransferedAtLocationBetweenTwoDates(Date startDate,Date endDate,String drugUUID,String pharmacyLocationUUID);
    public List<StockTransferTracker> getStockTransferTrackerNotApproved(PharmacyLocations pharmacyLocations);
    public StockTransferTracker saveStockTransferTracker(StockTransferTracker stockTransferTracker);
    public List<PharmacyStoreOutgoing> getPharmacyStoreOutgoingByStockTransferTracker(StockTransferTracker stockTransferTracker);
    public StockTransferTracker getStockTransferTrackerByUUID(String stockTransferTrackerUUID);
    public PharmacyStoreOutgoing getPharmacyOutgoingByUUID(String uuid);
    public List<PharmacyStore> getPharmacyStoreByLocationPlusRetired(PharmacyLocations locationUUID);
    public Integer quantityOfDrugsReceivedThroughTransfersBetweenTwoDates(Date startDate, Date endDate,PharmacyLocations pharmacyLocation,Drug drug);
    public List<DrugTransactions>  getDrugTransactionsByDrugAndLocation(Drug drug,PharmacyLocations pharmacyLocation,Date startDate, Date endDate);
    public List<DrugTransactions> getReceivedDrugTransactionsdBetweenDates(Date startDate, Date endDate,String pharmacyLocationUUID);
    public boolean savePharmacyDeliveryNotes(List<DeliveryNote> deliveryNotes);
    public DeliveryNoteTracker saveDeliveryNoteTracker(DeliveryNoteTracker deliveryNoteTracker);
    public List<DeliveryNoteTracker> getDeliveryNoteTrackerByLocation(PharmacyLocations pharmacyLocation);
    public List<DeliveryNote> getDeliveryNotesByDeliveryNoteTracker(DeliveryNoteTracker deliveryNoteTracker);
    public DeliveryNoteTracker getDeliveryNoteTrackerByUUID(String uuid);
    public List<PharmacyEncounter> getEncountersBetweenDates(Date minDate,Date maxDate,String location);
    public PatientFingerPrintModel identifyPatient(String fingerprint);
}
