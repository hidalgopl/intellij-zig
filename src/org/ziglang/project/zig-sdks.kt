package org.ziglang.project

import com.intellij.openapi.projectRoots.*
import icons.ZigIcons
import org.jdom.Element
import org.ziglang.ZIG_WEBSITE
import org.ziglang.ZigBundle
import java.nio.file.Files
import java.nio.file.Paths

/**
 * @deprecated
 */
class ZigSdkType : SdkType(ZigBundle.message("zig.name")) {
	override fun getPresentableName() = ZigBundle.message("zig.sdk.name")
	override fun getIcon() = ZigIcons.ZIG_BIG_ICON
	override fun getIconForAddAction() = icon
	override fun isValidSdkHome(sdkHome: String?) = validateJuliaSDK(sdkHome.orEmpty())
	override fun suggestSdkName(s: String?, p1: String?) = ZigBundle.message("zig.sdk.name")
	override fun suggestHomePath() = Paths.get(zigPath).parent?.parent?.toString()
	override fun getDownloadSdkUrl() = ZIG_WEBSITE
	override fun createAdditionalDataConfigurable(md: SdkModel, m: SdkModificator): AdditionalDataConfigurable? = null
	override fun getVersionString(sdkHome: String?) = versionOf(sdkHome.orEmpty())
	override fun saveAdditionalData(additionalData: SdkAdditionalData, element: Element) = Unit // leave blank
	override fun setupSdkPaths(sdk: Sdk, sdkModel: SdkModel): Boolean {
		val modificator = sdk.sdkModificator
		modificator.versionString = getVersionString(sdk) ?: ZigBundle.message("zig.sdk.unknown-version")
		modificator.commitChanges()
		return true
	}

	companion object InstanceHolder {
		val instance get() = SdkType.findInstance(ZigSdkType::class.java)
	}
}

fun validateJuliaSDK(sdkHome: String) = Files.isExecutable(Paths.get(sdkHome, "bin", "julia")) or
		Files.isExecutable(Paths.get(sdkHome, "bin", "julia.exe"))
