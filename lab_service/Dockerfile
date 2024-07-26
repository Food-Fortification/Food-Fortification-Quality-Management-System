FROM openjdk:17-alpine as builder
WORKDIR lab
RUN apk add --no-cache msttcorefonts-installer fontconfig
RUN mkdir -p /usr/share/fonts/truetype/ms-fonts
COPY src/main/resources/ms-fonts /usr/share/fonts/truetype/ms-fonts
RUN fc-cache -f -v
RUN apk add openssl
RUN echo "Q" | openssl s_client -connect iam-api-frqms.fssai.gov.in:443 >> /tmp/certificate
RUN keytool -importcert -file /tmp/certificate -alias fssai_frqms_cert -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit -noprompt
RUN echo "Q" | openssl s_client -connect fctest.fssai.gov.in:443 >> /tmp/certificate_fssai
RUN keytool -importcert -file /tmp/certificate_fssai -alias fssai_cert -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit -noprompt
COPY . .
RUN ls src/main/resources/
RUN ./mvnw clean install -s settings.xml -DskipTests
RUN mkdir -p /files
RUN mkdir -p /qrCodes
RUN mkdir -p /files/fortificationFiles
RUN mkdir -p /files/labFiles
RUN mkdir -p /files/manufacturerFiles
RUN mkdir -p /files/fortificationBatchQrCodes
RUN mkdir -p /files/fortificationLotQrCodes
ENTRYPOINT ["java", "-jar", "target/lab-exec.jar"]
