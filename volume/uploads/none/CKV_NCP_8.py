from NACLInboundCheck import NACLInboundCheck
from DB import Parameters


class NACLInbound20(NACLInboundCheck):
    def __init__(self) -> None:
        params = Parameters()
        param_list = params.get_param_env("CKV_NCP_8")

        super().__init__(check_id="CKV_NCP_8", ip_block=param_list[0]["value"], port=param_list[1]["value"])


check = NACLInbound20()