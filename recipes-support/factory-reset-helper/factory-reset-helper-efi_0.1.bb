#
# CIP Core, generic profile
#
# Copyright (c) Siemens AG, 2025
#
# Authors:
#  Quirin Gylstorff <quirin.gylstorff@siemens.com>
#
# SPDX-License-Identifier: MIT

require recipes-support/factory-reset-helper/factory-reset-helper.inc

DEBIAN_CONFLICTS = "factory-reset-helper-file"

# use systemd-factory-reset as default
# https://www.freedesktop.org/software/systemd/man/devel/systemd-factory-reset.html#EFI%20Variables
# The efivariable is defined by ${FACTORY_RESET_MARKER}-${FACTORY_RESET_EFIVARS_GUID}
FACTORY_RESET_MARKER ?= "FactoryResetRequest"
# Use a new vendor id instead of the one from systemd
FACTORY_RESET_EFIVARS_GUID ?= "abc688c9-fee3-41f7-87d3-1076e4a29e8f"
FACTORY_RESET_TYPE = "efivar"

DEBIAN_DEPENDS .= ", coreutils, util-linux, e2fsprogs, bsdextrautils, efivar"
