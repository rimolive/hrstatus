/*
    Copyright (C) 2012  Filippe Costa Spolti

    This file is part of Hrstatus.

    Hrstatus is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package br.com.hrstatus.controller;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.hrstatus.utils.Images;
import br.com.hrstatus.utils.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

/*
 * @author spolti
 */

@Resource
public class ImageController {

    private Logger log = Logger.getLogger(ImageController.class.getName());

    @Autowired
    private Result result;
    @Autowired
    private Validator validator;
    @Autowired
    private Images images;
    @Autowired
    private HttpServletRequest request;
    private UserInfo userInfo = new UserInfo();

    @Post("/uploud/logo/imagem")
    public void upload(final UploadedFile imagem) throws Exception {

        validator.onErrorRedirectTo(ConfigController.class).configServer();

        if (!imagem.getContentType().startsWith("image")) {
            validator.add(new ValidationMessage("The sended file is not a Image valid format.", "Erro"));
        } else {
            try {
                images.save(imagem);
                result.forwardTo(ConfigController.class).configServer();
            } catch (Exception e) {
                validator.add(new ValidationMessage("Unexpected error: " + e, "Erro"));

            }

        }

        validator.onErrorRedirectTo(ConfigController.class).configServer();
    }

    @Get("/delete/logo/imagem")
    public void delete() throws Exception {

        images.delete();
        result.forwardTo(ConfigController.class).configServer();

    }

    @Get("/show/logo/imagem/{type}")
    public File download(String type) throws IOException {

        try {
            if ("settings".equals(type) && !images.show("settings").equals(null)) {
                return images.show("settings");
            } else if ("home".equals(type) && !images.show("home").equals(null)) {
                return images.show("home");
            }
        } catch (javax.imageio.IIOException ex) {
            log.fine("Login page image not found or not configured yet.");
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    @Get("/show/emailHeader/{local}")
    public File emailHeader(String local) throws UnsupportedEncodingException {

        final String UPfileName = request.getRealPath("img/up.jpg");
        final String DOWNfileName = request.getRealPath("img/down.jpg");

        log.fine("FILE:  " + UPfileName);

        if ("up".equals(local)) {
            final File file = new File(UPfileName);
            return file;
        } else if ("down".equals(local)) {
            final File file = new File(DOWNfileName);
            return file;
        }

        return null;
    }
}