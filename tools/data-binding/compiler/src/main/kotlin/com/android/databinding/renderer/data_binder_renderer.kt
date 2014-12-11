/**
 * Created by yboyar on 11/8/14.
 */
package com.android.databinding.renderer

class DataBinderRenderer(val pkg: String, val projectPackage: String, val className: String, val renderers : List<ViewExprBinderRenderer> ) {
    fun render(br : BrRenderer) =
"""
package $pkg;
import $projectPackage.R;
import ${br.pkg}.${br.className};
public class $className implements com.android.databinding.library.DataBinderMapper {
    @Override
    public com.android.databinding.library.ViewDataBinder getDataBinder(android.view.View view, int layoutId) {
        switch(layoutId) {${renderers.map {"""
            case R.layout.${it.layoutName}:
                return new ${it.pkg}.${it.className}(view);"""
}.joinToString("\n            ")}
        }
        return null;
    }

    @Override
    public int getId(String key) {
        switch(key) {
            ${br.keyToInt.map({ "case \"${it.key}\": return  ${br.className}.${it.key};"}).joinToString("\n            ")}
        }
        return -1;
    }
}
"""
}
