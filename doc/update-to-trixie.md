# Update from Debian bookworm to Debian trixie

This document describes the changes necessary to build a Debian trixie image based isar-cip-core.
With Debian trixie cip-core will no longer build SWUpdate from source but will use SWUpdate
as provided by Debian trixie.

Updates from a system without signing to signing enabled on are possible with a signed swu.

## SWU signing is now mandatory

In isar-cip-core we use the debian snakeoil keys as signing samples. This keys must not be
used in production. To generate own keys you can follow the instructions in [README.signing.md](doc/README.signing.md#use-self-signed-certificates).  To enable signing with the
debian-snakeoil keys set the variable SWU_SIGNED to "1".
```
SWU_SIGNED = "1"
PREFERRED_PROVIDER_swupdate-certificates-key = "swupdate-certificates-key-snakeoil"
PREFERRED_PROVIDER_swupdate-certificates = "swupdate-certificates-snakeoil"
```
