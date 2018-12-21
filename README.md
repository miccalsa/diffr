# Diffr

Diffr is a test case for a microservice able to receive two binary resources (left and right) enconded on base 64 and give a result saying that:
  - Both resources are the same
  - A resource is larger than the other
  - When both resources are the same, point the positions where there are differences

### Solution

  - The test was done using Spring Boot, the microservice accepts two POST endpoings for placing the left and right resources. And one GET resource which yields the result of diffing both resources
  - For the test purposes, the resources are loaded and kept in memory using H2, with the intention of keeping a temporary store of the resources posted during the excecution of the application
  - The resources uses an ID to identify them, the application stores their respective left-rigth association, and yields an error when both or either of the resources are missing for the comparision.
  - **Assumptions:** For the usage of the endpoints, it was assumed the resource was going to be provided as a binary resource encoded with base 64. The endpoints accepts a JSON resource containing the data previously encoded.

### Usage
Run the Spring Boot application, it uses the default port "http://localhost:8080/"

| Action | End Point |
| ------ | ------ |
| Left Resource | http://localhost:8080/v1/diff/12/left |
| Right Resource | http://localhost:8080/v1/diff/12/right |
| Diffing Resources | http://localhost:8080/v1/diff/12 |

##### Resource (POST Body)
In order to provide the data for left and right resource, the POST should be sent as 'application/json' and should contain the body as follow:
```json
{
	"base64Data": "bG9yZz0gaXBzdW0GZsbG93Igc2l0IGFtZXQ="
}
```

##### Response
Once provided the resources for left and right, the Diffr can yield the result of the analysis as follows:
```json
{
    "result": "Both resources have same length",
    "insights": [
        "Difference found in position 5",
        "Difference found in position 16"
    ]
}
```

