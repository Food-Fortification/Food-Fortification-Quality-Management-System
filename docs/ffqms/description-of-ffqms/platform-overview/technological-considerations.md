# Technological Considerations

Some of the main considerations for building this software are as follows:

* Traceability — A single QR code stores the basic vendor info and the batch details. The system(In history tab, and batch history tree in the UI) stores the trace of all raw materials, their batch, and lot numbers along with quantities utilized for fortification. This ensures that any quality lapses can be traced back to the source.
* Immutability — To maintain traceability and QA/QC, it is crucial to secure and make the database immutable and encrypted. All tracking events go to a database built on top of blockchain principles, ensuring verifiability and immutability of data.
* Configurability — The platform can be configured across regions for workflows of other food fortification processes and to obfuscate manufacturing information in labs.
* Event Broadcast Framework — Since the platform is designed to work with different external systems, a framework allows interested clients to register for geographical state-level events and receive their login details for access.
* SSO - The platform integrates with Single Sign-On (SSO) through Keycloak to ensure seamless access management across different systems and applications. By utilizing SSO, users can securely authenticate once and gain access to all connected services, enhancing user experience while maintaining strong security protocols.&#x20;
* DPG Compliance — The platform is built following DPG Alliance standards.
