from __future__ import annotations

from checkov.common.models.enums import CheckResult, CheckCategories
from checkov.terraform.checks.resource.base_resource_check import BaseResourceCheck
from DB import Parameters


class LBTargetGroupUsingHTTPS(BaseResourceCheck):

    params = Parameters()
    param_list = params.get_param_env("CKV_NCP_15")

    def __init__(self, protocol):
        self.protocol = self.param_list[0]['value']
        name = f"Ensure Load Balancer Target Group is not using {self.protocol}."
        id = "CKV_NCP_15"
        supported_resources = ("ncloud_lb_target_group",)
        categories = (CheckCategories.GENERAL_SECURITY,)
        super().__init__(name=name, id=id, categories=categories, supported_resources=supported_resources)

    def scan_resource_conf(self, conf):
        if "protocol" in conf.keys():
            if conf.get("protocol") != [self.protocol] and conf.get("protocol") !=["HTTPS"]:
                return CheckResult.PASSED
        return CheckResult.FAILED


check = LBTargetGroupUsingHTTPS()
