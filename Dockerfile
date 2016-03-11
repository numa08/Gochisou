FROM ubuntu:14.04
MAINTAINER numa08 "n511287@gmail.com"
# Install java7
RUN apt-get install -y software-properties-common && add-apt-repository -y ppa:webupd8team/java && apt-get update
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
RUN apt-get install -y oracle-java8-installer
# Install Deps
RUN dpkg --add-architecture i386 && apt-get update && apt-get install -y --force-yes expect git wget libc6-i386 lib32stdc++6 lib32gcc1 lib32ncurses5 lib32z1 python curl
# Install Android SDK
RUN cd /opt && wget --output-document=android-sdk.tgz --quiet http://dl.google.com/android/android-sdk_r24.4.1-linux.tgz && tar xzf android-sdk.tgz && rm -f android-sdk.tgz && chown -R root.root android-sdk-linux
# Setup environment
ENV ANDROID_HOME /opt/android-sdk-linux
ENV PATH ${PATH}:${ANDROID_HOME}/tools:${ANDROID_HOME}/platform-tools
# Install sdk elements
RUN echo y | /opt/android-sdk-linux/tools/android update sdk --all --force --no-ui --filter platform-tools,tools,build-tools-21,build-tools-21.0.1,build-tools-21.0.2,build-tools-21.1,build-tools-21.1.1,build-tools-21.1.2,build-tools-22,build-tools-22.0.1,build-tools-23.0.2,android-21,android-22,android-23,addon-google_apis_x86-google-21,extra-android-support,extra-android-m2repository,extra-google-m2repository,extra-google-google_play_services,sys-img-armeabi-v7a-android-21
# Create emulator
# RUN echo "no" | /opt/android-sdk-linux/tools/android create avd \
#                 --force \
#                 --device "Nexus 5" \
#                 --name test \
#                 --target android-21 \
#                 --abi armeabi-v7a \
#                 --skin WVGA800 \
#                 --sdcard 512M
# Cleaning
RUN apt-get clean
# GO to workspace
RUN mkdir -p /opt/workspace
ADD . /opt/workspace
WORKDIR /opt/workspace
CMD echo "sdk.dir=/opt/android-sdk-linux" > local.properties; ./gradlew test
