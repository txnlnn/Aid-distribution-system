# Aid Distribution System

The Aid Distribution System is a Java-based project that facilitates the matching of aid donations from donors to NGOs (Non-Governmental Organizations) through a Distribution Center (DC). The system allows donors, NGOs, and the DC to perform specific tasks to manage the aid distribution process.

## Features

- **Donor**: 
  - Register and login to donate aid items.
  - View donation history.
  
- **NGO**: 
  - Register and login to request aid items.
  - View received aid items.
  - Queue at the DC to collect aid items.
    - FIFO Mode: First-in, first-out queueing.
    - Priority Mode: Priority based on NGO manpower.
  
- **DC (Distribution Center)**: 
  - View donated aid items and registered NGOs.
  - Match aid items between donors and NGOs.
  - Keep track of aid item status:
    - Available: Aid item is still available.
    - Reserved: Aid item has been reserved by an NGO.
    - Collected: Aid item has been collected by an NGO.
