#
# Copyright (c) Siemens AG, 2023-2025
#
# SPDX-License-Identifier: MIT

include opensbi.inc

inherit deploy-firmware

SRC_URI += "file://qemu-riscv64-rules"

SRC_URI[opensbi.sha256sum] = "2cf856a4e5e2e052948ddb54ba48232b1f698b7f52e0374fc7d17d51e8c8f7ce"

DEPENDS += "u-boot-qemu-riscv64"
DEBIAN_BUILD_DEPENDS .= ", u-boot-qemu-riscv64"

OPENSBI_BIN = "fw_payload.bin"

do_deploy_firmware() {
    dpkg --fsys-tarfile "${WORKDIR}/${PN}_${PV}_${DISTRO_ARCH}.deb" | \
        tar xOf - "./usr/lib/opensbi/${MACHINE}/${OPENSBI_BIN}" \
        > "${DEPLOYDIR_FIRMWARE}/${OPENSBI_BIN}"
}
