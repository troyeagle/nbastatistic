package njuse.ffff.uiservice;

/**
 * 搜索界面的访问和修改方法
 * 
 * @author Li
 *
 */
public interface SearchService {

	/**
	 * 提供搜索时非空的限定条件
	 * 
	 * @return <code>arr[i][0]</code>: 条件名<br>
	 *         <code>arr[i][1]</code>: 限定条件1<br>
	 *         <code>arr[i][2]</code>: 限定条件2（若没有则为<code>null</code>）
	 */
	String[][] getFilters();
}
