package com.dorayd.sports.features.team;

import com.dorayd.sports.features.team.models.Team;
import com.dorayd.sports.features.user.UserTestHelper;
import com.dorayd.sports.features.user.models.User;

public class TeamTestHelper {
    private TeamTestHelper() {}

    public static boolean isTeamEqual(Team a, Team b) {
        if(!a.getName().equals(b.getName())) return false;

        for(User aPlayer : a.getPlayers()) {
            if(!isUserInTeam(aPlayer, b)) return false;
        }
        return true;
    }

    public static boolean isUserInTeam(User user, Team team) {
        for(User player : team.getPlayers()) {
            if(UserTestHelper.isUserEqual(player, user)) return true;
        }
        return false;
    }
}
