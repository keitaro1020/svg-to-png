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
        <table>
            <thead>
                <tr>
                    <th>id</th>
                    <th>svg url</th>
                    <th>svg 画像</th>
                    <th>png url</th>
                </tr>
            </thead>
            <tbody>
            <#list urls as url>
                <tr>
                    <td>${url.id?string(0)}</td>
                    <td>${url.url}</td>
                    <td><img src="${url.url}" width="100px"></td>
                    <td><a href="${url.pngUrl}">${url.pngUrl}</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </body>
</html>
