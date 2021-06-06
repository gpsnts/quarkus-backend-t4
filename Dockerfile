####
# STEPS:
# 1. ./gradlew build
# 2. docker build . -t quarkus/quarkus-backend-t4 (root)
# 3. docker run -i --rm --name quarkus-backend-t4 --env PORT=8080 -p 8080:8080 quarkus/quarkus-backend-t4

FROM registry.access.redhat.com/ubi8/ubi-minimal:8.3

WORKDIR /work/
RUN chown 1001 /work && chmod "g+rwX" /work && chown 1001:root /work
COPY --chown=1001:root build/*-runner /work/application
RUN chmod 775 /work

CMD ./application -Dquarkus.http.host=0.0.0.0 -Dquarkus.http.port=${PORT}
