/*
    mclmaf2025
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclmaf2025
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclmaf2025.common.util;

import com.mclegoman.mclmaf2025.common.data.Data;
import com.terraformersmc.modmenu.config.ModMenuConfig;

public class Compatibility {
	public static String getModsButtonStyle() {
		if (Data.isModInstalled("modmenu")) {
			return ModMenuConfig.MODS_BUTTON_STYLE.getValue().name();
		} else return "none";
	}
}