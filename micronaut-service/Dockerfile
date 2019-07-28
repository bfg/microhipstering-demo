#
# Dockerfile
#
# Reference: https://docs.docker.com/engine/reference/builder/
#

# inherit official openjdk jre image
FROM          adoptopenjdk/openjdk12:jre

# we would like to reuse the same dockerfile without modifications
# when the service artifact file changes
ARG           appJar

# create app folder and copy service starter
RUN           mkdir -p /app
COPY          java-app-starter /usr/local/bin

# copy the app archive
COPY          ${appJar:-app.jar} /app

USER          nobody
ENTRYPOINT    [ "/usr/local/bin/java-app-starter" ]

# vim:shiftwidth=2 softtabstop=2 expandtab
# EOF
