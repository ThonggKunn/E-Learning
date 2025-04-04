package src.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import src.constants.UrlConstant;
import src.dto.request.admin.CourseCreateReq;
import src.dto.request.admin.CourseSearchReq;
import src.dto.request.admin.CourseUpdateReq;
import src.dto.response.admin.CourseSearchRes;
import src.entity.Course;
import src.service.ICourseService;

@RestController
@RequestMapping(UrlConstant.API_V1)
@RequiredArgsConstructor
public class CourseController {

    private final ICourseService courseService;

    /**
     * Tạo mới một khóa học.
     *
     * @param req đối tượng {@link CourseCreateReq} chứa thông tin course cần tạo.
     * @return một ResponseEntity chứa đối tượng {@link Course} đã được tạo.
     * @see ICourseService#createCourse(CourseCreateReq)
     */
    @PostMapping(UrlConstant.ADD_COURSES)
    public ResponseEntity<?> addCourse(@RequestBody CourseCreateReq req) {
        Course createdCourse = courseService.createCourse(req);
        return ResponseEntity.ok(createdCourse);
    }

    /**
     * Update thông tin một khóa học cụ thể.
     *
     * @param courseId ID của khóa học cần cập nhật.
     * @param req      đối tượng {@link CourseUpdateReq} chứa thông tin update cho khóa học.
     * @return         ResponseEntity chứa đối tượng {@link Course} đã được cập nhật.
     * @see ICourseService#updateCourse(Long, CourseUpdateReq)
     */
    @PutMapping(UrlConstant.UPDATE_COURSES)
    public ResponseEntity<?> updateCourse(@PathVariable("course_id") Long courseId,
                                          @RequestBody CourseUpdateReq req) {
        Course updatedCourse = courseService.updateCourse(courseId, req);
        return ResponseEntity.ok(updatedCourse);
    }

    /**
     * Xóa một khóa học cụ thể (soft delete)
     *
     * @param courseId ID của course cần xóa.
     * @return         ResponseEntity với thông báo thành công.
     * @see ICourseService#softDeleteCourse(Long)
     */
    @DeleteMapping(UrlConstant.DELETE_COURSES)
    public ResponseEntity<?> softDeleteCourse(@PathVariable("course_id") Long courseId) {
        courseService.softDeleteCourse(courseId);
        return ResponseEntity.ok("Course soft deleted successfully.");
    }

    /**
     * Lấy danh sách khóa học theo phân trang dựa trên các tiêu chí tìm kiếm.
     * <p>
     * Các tiêu chí tìm kiếm bao gồm các field như tên khóa học, trạng thái, tên giáo viên và khoảng thời gian.
     * </p>
     *
     * @param page        số trang cần lấy, mặc định là 0.
     * @param pageSize    số lượng bản ghi trên mỗi trang, mặc định là 10.
     * @param sort        trường -> kết quả sẽ được sắp xếp, mặc định là "created_date".
     * @param req         đối tượng {@link CourseSearchReq} chứa các bộ lọc tìm kiếm.
     * @return            ResponseEntity chứa {@link CourseSearchRes} với kết quả tìm kiếm.
     * @see ICourseService#getCourses(int, int, String, CourseSearchReq)
     */
    @GetMapping(UrlConstant.GET_COURSES)
    public ResponseEntity<CourseSearchRes> getCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "created_date") String sort,
            @RequestBody CourseSearchReq req) {

        CourseSearchRes res = courseService.getCourses(page, pageSize, sort, req);
        return ResponseEntity.ok(res);
    }
}
