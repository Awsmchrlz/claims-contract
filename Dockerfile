# Use Fabric Java environment as base image
FROM hyperledger/fabric-javaenv:2.5

# Set working directory inside the container
WORKDIR /chaincode

# Copy the compiled jar into the container (Maven output)
# Assuming you run `mvn clean package` and the jar is in target/*.jar
COPY target/claim-contract-1.0.0.jar app.jar

# Expose the port for CCAAS communication
EXPOSE 9999

# Run the jar when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
