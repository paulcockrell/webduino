# Clojure / Arduino / Firmata / ClojureScript Spa

## References

<https://github.com/DavidVujic/sente-with-reagent-and-re-frame/tree/master>

## Prerequisits

1. Clojure / Java
1. watchexec

## Dev

### Backend

Once your Arduino Board is connected you must find the port it is connected on. Use the following to find this out. You must then use the value in the environment variable `SERIAL_PORT` when booting the backend process.

```bash
ls -la /dev/tty*|grep usbserial
```

```bash
SERIAL_PORT=/dev/tty.usbserial-110 watchexec -r -e clj -- clojure -M -m myapp.backend.main
```

### Frontend

```bash
clojure -M:dev
```

### Docker

This is more of a problem when on a mac as you have to forward the serial port into the containers

#### MAC instructions

If on a Mac, in the `docker-compose` file, uncomment the `environment` key `SERIAL_PORT`

Determine the port the arduino is on

```bash
ls -la /dev/tty*|grep usbserial
```

Run the following to forward the host port to docker, update the `FILE:` value to be your Arduino Board port discovered in the previous step

```bash
socat -d -d TCP-LISTEN:54321,reuseaddr,fork FILE:/dev/tty.usbserial-110,raw
```

```bash
docker-compose up
```

#### Linux

It should just work as Docker has access to the host ports, update the `SERIAL_PORT` key in the `docker-compose` file to point to the Arduino Port on the host.

```bash
docker-compose up
```

## Arduino starter kit info

In the IDE the board options are:
Board: Arduino UNO
Baud rate: 115200 (With factory firmware on the kit)
Baud rate: 57600 (With Firmata on the kit)

Pin out [link](https://www.elecrow.com/wiki/All-in-one_Starter_Common_Board_Kit_for_Arduino.html)

### Problems on Mac

If you have Apple silicon you may have issues with old 32bit drivers.

To get the Arduino IDE to upload to the board I had to delete the 32bit board
manager first:

```bash
rm -rf ~/Library/Arduino15/packages/arduino/tools/avrdude/<the-older-version>
```
