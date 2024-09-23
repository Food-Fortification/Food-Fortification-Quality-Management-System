FROM openjdk:17-alpine as builder
WORKDIR fortification
RUN apk add --no-cache msttcorefonts-installer fontconfig
RUN mkdir -p /usr/share/fonts/truetype/ms-fonts
COPY src/main/resources/ms-fonts /usr/share/fonts/truetype/ms-fonts
RUN fc-cache -f -v
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
ENTRYPOINT ["java", "-jar", "target/fortification-exec.jar"]
