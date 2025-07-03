FROM openjdk:8-jdk

# Install Maven and essential tools
RUN apt-get update && \
    apt-get install -y maven git curl wget unzip vim net-tools && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Set environment variables
ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
ENV MAVEN_HOME=/usr/share/maven
ENV PATH=$PATH:$MAVEN_HOME/bin

# Create non-root user
RUN useradd -m -s /bin/bash developer && \
    mkdir -p /home/developer/.m2 && \
    chown -R developer:developer /home/developer

# Set working directory
WORKDIR /workspace

# Switch to non-root user
USER developer

# Keep container running
CMD ["tail", "-f", "/dev/null"]