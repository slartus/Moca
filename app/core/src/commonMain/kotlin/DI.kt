import org.kodein.di.bindings.Scope
import org.kodein.di.bindings.ScopeRegistry
import org.kodein.di.bindings.StandardScopeRegistry


data class Catalog(
    var userData: ScopeRegistry = StandardScopeRegistry()
)

object CatalogScope : Scope<Catalog> {
    override fun getRegistry(context: Catalog): ScopeRegistry =
        context.userData as? ScopeRegistry
            ?: StandardScopeRegistry().also { context.userData = it }
}