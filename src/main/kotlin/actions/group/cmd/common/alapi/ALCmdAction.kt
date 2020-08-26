package actions.group.cmd.common.alapi

import actions.interfaces.CmdAction
import constants.Constants

interface ALCmdAction : CmdAction {

    override fun supportGroupIds(): List<Long>? {
        return Constants.testGroups
    }

}