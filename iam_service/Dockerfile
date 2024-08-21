FROM openjdk:17-alpine as builder
WORKDIR iam
RUN apk add --no-cache msttcorefonts-installer fontconfig
RUN mkdir -p /usr/share/fonts/truetype/ms-fonts
COPY src/main/resources/ms-fonts /usr/share/fonts/truetype/ms-fonts
RUN fc-cache -f -v
RUN apk add openssl
COPY . .
RUN ls src/main/resources/
RUN ./mvnw package
RUN mkdir -p /files
RUN mkdir -p /qrCodes
RUN mkdir -p /files/fortificationFiles
RUN mkdir -p /files/labFiles
RUN mkdir -p /files/manufacturerFiles
RUN mkdir -p /files/fortificationBatchQrCodes
RUN mkdir -p /files/fortificationLotQrCodes
RUN ls  target -larth
ENTRYPOINT ["java", "-jar", "target/iam-exec.jar"]
