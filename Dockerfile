# Stage 1: Build purejavacomm
FROM maven:3.9-eclipse-temurin-17 as builder

RUN git clone https://github.com/nyholku/purejavacomm.git /purejavacomm
WORKDIR /purejavacomm
RUN mvn install

# Stage 2: Final image
FROM timbru31/java-node:latest

# Copy built JARs
COPY --from=builder /root/.m2 /root/.m2

# Install Clojure CLI
RUN curl -O https://download.clojure.org/install/linux-install-1.11.1.1413.sh && \
    chmod +x linux-install-1.11.1.1413.sh && \
    ./linux-install-1.11.1.1413.sh && \
    rm linux-install-1.11.1.1413.sh

RUN apt-get update && \
    apt-get install -y socat && \
    apt-get clean

WORKDIR /app

# Pre-copy deps files for layer caching
COPY deps.edn ./

# Pre-warm Clojure deps
RUN clojure -P

# Optional: cache .m2/.cpcache to avoid re-downloading
ENV CLOJURE_CLI_DEPENDENCY_CACHE=/root/.m2
ENV CLOJURE_CLI_CPCACHE=/root/.cpcache

# Copy Node deps early to cache npm install
COPY package.json shadow-cljs.edn ./
RUN npm install

# Now copy the full app
COPY . .

# Ensure entrypoint is executable
RUN chmod +x ./start.sh

EXPOSE 3000 3001 9630

CMD ["sh", "-c", "/app/start.sh"]
