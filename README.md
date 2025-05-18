# PharmaSupplyChainAndPrescriptionFulfillmentSystem

## Project Overview
a Pharma Supply Chain and Prescription Fulfillment System for a pharmaceutical company. The system will manage the inventory of drugs, prescriptions, and pharmacies, as well as the logistics of fulfilling patient prescriptions.
## Prerequisites
- **Java 17+**
- **Gradle 3.13+**
- **PostgreSQL 14+**
- **Docker 28.1.1+**
- **Docker-compose 2.36.0+**
## Setup Instructions
```shell
docker-compose up -d postgres
```
```shell
./gradlew clean build
```
## API Documentation
### Add a Drug
#### `POST /drugs/`
**Function**： Add a drug to the inventory, specifying its details and stock.  
**RequestBody**：
```json  
{
  "name": "aspirin",
  "manufacturer": "Bayer",
  "batchNumber": "20230515",
  "expiryDate": "2025-12-31",
  "stock": 100
}
```
**Response**
```json
{
  "name": "aspirin",
  "stock": 100
}
```
### List Pharmacies
#### `GET /pharmacies/`
**Function**： Retrieve all pharmacies and their contracted drugs.
**Response**
```json
[
  {
    "pharmacyName": "testPharmacy1",
    "pharmacyAdd": "Sichuan",
    "drugsInfo": {
      "aspirin": 50
    }
  },
  {
    "pharmacyName": "testPharmacy2",
    "pharmacyAdd": "Sichuan",
    "drugsInfo": {
      "aspirin": 40
    }
  }
]
```
### Create a Prescription
#### `POST /prescriptions/`
**Function**: Submit a prescription for a patient at a specific pharmacy.
**RequestBody**
```json
{
    "pharmacyId" : "2",
    "patientId": "123",
    "drugs": {
        "1" : 20
    }
}
```
**Response**
```json
{
  "drugIds": {
    "1": 20
  },
  "pharmacyId": "2",
  "prescriptionId": "3",
  "patientId": "123",
  "status": "SUCCESS",
  "errorMessage": ""
}
```
### Fulfill a Prescription
#### `POST /prescriptions/{prescriptionId}/fulfillment`
**Function**: Submit a prescription for a patient at a specific pharmacy.
**Response**
```json
{
  "drugIds": {
    "1": 20
  },
  "pharmacyId": "2",
  "prescriptionId": "4",
  "patientId": "123",
  "status": "SUCCESS",
  "errorMessage": ""
}
```
### Get Audit Logs
#### `GET /auditLogs`
**Function**: Submit a prescription for a patient at a specific pharmacy.
**RequestParam**
```json
{
  "status": "SUCCESS",
  "patientId": 123,
  "pharmacyId": 1
}
```
**Response**
```json
[
  {
    "id": 1,
    "patientId": 123,
    "pharmacyId": 1,
    "prescriptionId": 1,
    "drugsRequested": [
      "1"
    ],
    "drugsDispensed": {
      "1": 20
    },
    "failureReason": "",
    "attemptTime": "2025-05-18T21:58:41.280153",
    "status": "SUCCESS"
  },
  {
    "id": 4,
    "patientId": 123,
    "pharmacyId": 1,
    "prescriptionId": 1,
    "drugsRequested": [
      "1"
    ],
    "drugsDispensed": {
      "1": 20
    },
    "failureReason": "",
    "attemptTime": "2025-05-18T21:59:02.911538",
    "status": "SUCCESS"
  }
]
```
## Testing Instructions
**Test frameworks**: Junit, Mockito
**Commands to run tests**: 
```shell
./gradlew test
```