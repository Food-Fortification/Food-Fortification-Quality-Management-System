# Detailed Steps for initial setup with DSL

1. Product is added with name and description
2. Categories are created from the list of categories
   * Populate the "outside\_platform” field to indicate if the category is managed outside the fortification platform.
   * Link each category to the product.
3. Stage and Workflow Entry Creation:
   * For each stage in the stages list there exists an entry in workflow indicating its actions
4. Workflow Entries
   * Categories present in raw material source and target are created if not existing
   * “USER” and “ADMIN” roles are created given MODULE and LAB
   * If the type is “creation”
     1. Actions are added for creating lots for raw materials with source category as raw materials and base category as category in workflow
     2. Actions are added for creating batches with raw materials and sending them to target categories
     3. Actions are added for sending batches to lab based on dispatch lab option
     4. “APPROVER” role is created for target categories which has actions to approve a batch before being received at the target category module
     5. Based on the dispatch lab option lab actions are also added.
   * If the type is “dispatch”
     1. Actions are created for creating category lots from the base category to dispatch it to target categories manufacturers/units
     2. Base category module is identified with workflow stage name
     3. Based on dispatch lab option lab actions are added
5. Once the roles and role categories are populated, added roles are created in the keycloak UI
6. Assign the required roles to the users created to get the appropriate actions for categories