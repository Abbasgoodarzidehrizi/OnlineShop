package net.abbas.app.controller.panel.file;
import net.abbas.app.annotation.CheckPermission;
import net.abbas.app.controller.base.DeleteController;
import net.abbas.app.controller.base.ReadController;
import net.abbas.app.model.APIPaginationResponse;
import net.abbas.app.model.APIResponse;
import net.abbas.app.model.enums.APIStatus;
import net.abbas.dto.file.FileDto;
import net.abbas.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
@RestController
@RequestMapping("/api/panel/file")
public class FilePanelController implements ReadController<FileDto>,
        DeleteController<FileDto>{

    private final FileService fileService;

    @Autowired
    public FilePanelController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("upload")
    @CheckPermission("add-file")
    public APIResponse<FileDto> upload(@RequestParam("file") MultipartFile file ) throws Exception {
    try {
        return APIResponse.<FileDto>builder()
                .status(APIStatus.Success)
                .message("Ok")
                .data(fileService.uploadFile(file))
                .build();
    }catch (Exception e) {
        return APIResponse.<FileDto>builder()
                .status(APIStatus.Error)
                .message("Error")
                .build();
    }
    }

    @Override
    @CheckPermission("delete_file")
    public APIResponse<Boolean> delete(long id) {
        return APIResponse.<Boolean>builder()
                .data(fileService.delete(id))
                .status(APIStatus.Success)
                .message("Ok")
                .build();
    }

    @Override
    @CheckPermission("list_file")
    public APIPaginationResponse<List<FileDto>> getAll(Integer page, Integer size) {
        Page<FileDto> data = fileService.readAll(page, size);
        return APIPaginationResponse.<List<FileDto>>builder()
                .message("OK")
                .data(data.getContent())
                .status(APIStatus.Success)
                .totalCount(data.getTotalElements())
                .totalPage(page)
                .build();
    }
}
