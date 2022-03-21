/**
* main page object containing all methods, selectors and functionality
* that is shared across all page objects
*/
export default class Page {
    /**
    * Opens a sub page of the page
    * @param path path of the sub page (e.g. /path/to/page.html)
    */
    public async open (path: string) {
        const url = await browser.getUrl();
        console.info(`Current on ${url}`);
        
        if (path && !url.endsWith(path)) {
            console.info(`Navigate to ${url}/${path}`);
            return await browser.url(`/${path}`)
        }
    }
}
