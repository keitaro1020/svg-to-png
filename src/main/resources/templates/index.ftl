<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="utf-8">
        <title>SVG to PNG</title>
    </head>
    <body>
        <form role="form" action="" method="post">
            <div>
                <label for="message">URL</label>
                <input type="url" name="url" id="url" required autofocus/>
            </div>
            <button type="submit">submit</button>
        </form>

        <hr>
        <table BORDER="1" CELLSPACING="0">
            <thead>
                <tr>
                    <th>svg 画像</th>
                    <th>png url</th>
                </tr>
            </thead>
            <tbody>
            <#list urls as url>
                <tr>
                    <td><a href="${url.url}"><img src="${url.url}" width="100px"></a></td>
                    <td><a href="${url.pngUrl}">${url.pngUrl}</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </body>
</html>
