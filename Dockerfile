FROM ubuntu:latest
LABEL authors="suraj"

ENTRYPOINT ["top", "-b"]