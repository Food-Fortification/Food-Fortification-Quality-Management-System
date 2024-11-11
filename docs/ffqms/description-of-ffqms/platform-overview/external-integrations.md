# External Integrations

### **Subscription Service for external platforms**

#### **Integration of Existing Portals with Central QA/QC Platform**

The fortification platform is planned for implementation across multiple states in India, some of which already have independent working portals. To ensure seamless integration of these existing portals with the central QA/QC platform, we need to address the challenge of integrating multiple systems, which involves customizing APIs and developing new adapters for data formatting, consumption, and publishing.

**Proposed Solution: Webhook Integration**

To facilitate this integration, we offer a solution where stakeholders can:

1. **Register Webhook URLs:** Stakeholders can register their webhook URLs on the central QA/QC platform.
2. **Subscribe to State Events:** Stakeholders can subscribe to events from specific states of interest.
3. **Receive Event Notifications:** Whenever an event is triggered in the central platform, the registered webhook URLs will be called.
4. **Handle POST Data:** Stakeholders will receive POST data containing event information, which they can then save, format, and process according to their needs.

#### **Prerequisites for integration**:&#x20;

The manufacturer data should be unique across all platforms across India. Any new platform/state trying to integrate with QA/QC platform should have the manufacturers synced. Currently fssai license number is considered as unique constraint to avoid duplicates if there are Mutiple platforms are integrated for a single geographical state. Attached below is the format for the manufacturers bulk import into the QA/QC platform.&#x20;

Sample Format:

{% file src="../../../.gitbook/assets/Manufacturers_Template.xlsx" %}

#### **How Authentication works**:&#x20;

Upon a client's initial registration within the QA/QC platform, A client id and client secret specific to the client is generated within our authorization server. Clients are expected to store the provided credentials in a secured store and use the credentials for getting access tokens.

#### Pushing data to QA/QC platform:&#x20;

To submit data to the QA/QC platform, the registered subscriber must first authenticate by invoking the login API with their client ID and client secret obtained during initial registration. This will generate an access token, which is necessary for all subsequent interactions with the system. The platform adheres to the OpenAPI specification and offers Swagger endpoints, allowing the subscriber to effectively use the available APIs for data submission. By referencing the OpenAPI documentation, the subscriber can construct the appropriate request payload to successfully push event data into the QA/QC platform.

#### **Possible events:**

&#x20;These are the possible events from the platform.

1. BatchCreated
2. BatchUpdated
3. BatchSampleSentToLab
4. BatchSampleRejected
5. BatchTestPassed
6. BatchTestFailed
7. BatchRejected
8. BatchApproved
9. LotCreated
10. LotSampleSentToLab
11. LotSampleRejected
12. LotTestPassed
13. LotTestFailed
14. LotRejected
15. LotApproved
16. LotDispatched
17. LotReceived
18. LotSentBack
19. LotRejectedReceived

#### **Use case Example**:

Integration can occur whether the QA/QC platform is central or if the state portal is central. Here’s an example of how integration works when the QA/QC platform is central and a state portal (e.g., Andhra Pradesh) is central for specific functions:

1. **Dispatch Event Trigger:**
   1. When an FRK lot is dispatched from an FRK manufacturer to the FRK Godown, an event is triggered on the QA/QC platform.
   2. The state portal, having registered with the QA/QC platform, receives this event notification and saves the lot information into its system.
2. **Lot Acknowledgment:**
   1. Once the lot arrives at the FRK Godown, the state portal acknowledges receipt of the lot.
   2. This acknowledgment is pushed to the QA/QC platform using Swagger APIs in the background from the state portal.
3. **Sample Testing:**
   1. Samples from the lot can be sent to the lab via the state portal.
   2. The sample-related data is pushed to the QA/QC platform through Swagger APIs.
4. **Lab Test Results:**
   1. After lab testing is completed on the QA/QC platform’s Lab portal, LotTestPassed or LotTestFailed events are triggered based on the test results.
   2. These events are sent to the state portal through the registered webhook.
   3. The state portal downloads and saves the lab results from the webhook data and displays the results in its UI.
5. **Lot Approval or Rejection:**
   1. Based on the lab results, the lot can be approved or rejected from the state portal.
   2. The approval or rejection status is then pushed to the QA/QC platform in the background using Swagger APIs.

