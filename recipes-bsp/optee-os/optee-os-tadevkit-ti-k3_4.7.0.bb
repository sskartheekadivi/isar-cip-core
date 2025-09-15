#
# CIP Core, generic profile
#
# Copyright (c) Texas Instruments Incorporated, 2025
#
# Authors:
#  Sai Sree Kartheek Adivi <s-adivi@ti.com>
#
# SPDX-License-Identifier: MIT
#

OPTEE_ENABLE_APPS = "0"

require recipes-bsp/optee-os/optee-os-tadevkit-custom.inc
require optee-os-ti-k3_${PV}.inc
