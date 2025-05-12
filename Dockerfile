FROM timbru31/java-node:latest

# Install Clojure CLI
RUN curl -O https://download.clojure.org/install/linux-install-1.11.1.1413.sh && \
    chmod +x linux-install-1.11.1.1413.sh && \
    ./linux-install-1.11.1.1413.sh && \
    rm linux-install-1.11.1.1413.sh

WORKDIR /app

# Install frontend deps
COPY package.json shadow-cljs.edn ./
RUN npm install

# Copy the rest of the app
COPY . .

EXPOSE 3000 3001 9630

CMD ["sh", "-c", "clojure -M -m myapp.core & clojure -M:dev"]

