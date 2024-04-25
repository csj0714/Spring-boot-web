/*
 * package com.example.test.upload;
 * 
 * import java.io.File; import java.io.IOException; import java.net.URLDecoder;
 * import java.nio.charset.StandardCharsets; import java.nio.file.Files; import
 * java.nio.file.Path; import java.nio.file.Paths; import java.time.LocalDate;
 * import java.time.format.DateTimeFormatter; import java.util.ArrayList; import
 * java.util.List; import java.util.Objects; import java.util.UUID;
 * 
 * import org.springframework.beans.factory.annotation.Value; import
 * org.springframework.http.HttpHeaders; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.util.FileCopyUtils; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RestController; import
 * org.springframework.web.multipart.MultipartFile;
 * 
 * import lombok.extern.log4j.Log4j2; import lombok.extern.slf4j.Slf4j; import
 * net.coobird.thumbnailator.Thumbnailator;
 * 
 * @RestController
 * 
 * @Slf4j public class UploadController {
 * 
 * @Value("${com.example.ex8_fileupload.upload.path}") // application 의
 * properties 의 변수 private String uploadPath;
 * 
 * 
 * @GetMapping("/uploadEx") public String uploadEx() { return
 * "upload/uploadAjax"; // uploadAjax.html 파일을 찾아서 반환 }
 * 
 * 파일 업로드, 업로드 결과 반환
 * 
 * @PostMapping("/uploadAjax") public ResponseEntity<List<UploadResultDTO>>
 * uploadFile(MultipartFile[] uploadFiles) {
 * 
 * List<UploadResultDTO> resultDTOList = new ArrayList<>();
 * 
 * for (MultipartFile uploadFile: uploadFiles) {
 * 
 * // 이미지 파일만 업로드 if
 * (!Objects.requireNonNull(uploadFile.getContentType()).startsWith("image")) {
 * log.warn("this file is not image type"); return new
 * ResponseEntity<>(HttpStatus.FORBIDDEN); }
 * 
 * // 실제 파일 이름 IE나 Edge는 전체 경로가 들어오므로 => 바뀐 듯 .. String orginalName =
 * uploadFile.getOriginalFilename(); assert orginalName != null; String fileName
 * = orginalName.substring(orginalName.lastIndexOf("\\") + 1);
 * 
 * log.info("fileName: "+fileName);
 * 
 * // 날짜 폴더 생성 String folderPath = makeFolder();
 * 
 * // UUID String uuid = UUID.randomUUID().toString();
 * 
 * // 저장할 파일 이름 중간에 "_"를 이용해서 구현 String saveName = uploadPath + File.separator +
 * folderPath + File.separator + uuid + "_" + fileName;
 * 
 * Path savePath = Paths.get(saveName);
 * 
 * try { // 원본 파일 저장 uploadFile.transferTo(savePath);
 * 
 * // 섬네일 생성 String thumbnailSaveName = uploadPath + File.separator + folderPath
 * + File.separator + "s_" + uuid + "_" + fileName;
 * 
 * // 섬네일 생성 File thumbnailFile = new File(thumbnailSaveName);
 * Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100, 100);
 * 
 * resultDTOList.add(new UploadResultDTO(fileName, uuid, folderPath));
 * 
 * } catch (IOException e) { e.printStackTrace(); }
 * 
 * } return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
 * 
 * }
 * 
 * 날짜 폴더 생성 private String makeFolder() {
 * 
 * String str =
 * LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
 * 
 * String folderPath = str.replace("/", File.separator);
 * 
 * // make folder -------- File uploadPathFolder = new File(uploadPath,
 * folderPath);
 * 
 * if(!uploadPathFolder.exists()) { boolean mkdirs = uploadPathFolder.mkdirs();
 * log.info("-------------------makeFolder------------------");
 * log.info("uploadPathFolder.exists(): "+uploadPathFolder.exists());
 * log.info("mkdirs: "+mkdirs); }
 * 
 * return folderPath;
 * 
 * } 업로드 이미지 출력하기
 * 
 * @GetMapping("/display") public ResponseEntity<byte[]> getFile(String
 * fileName) {
 * 
 * ResponseEntity<byte[]> result;
 * 
 * try { String srcFileName = URLDecoder.decode(fileName,
 * StandardCharsets.UTF_8);
 * 
 * log.info("fileName: " + srcFileName);
 * 
 * File file = new File(uploadPath + File.separator + srcFileName);
 * 
 * log.info("file: " + file);
 * 
 * HttpHeaders header = new HttpHeaders();
 * 
 * 
 * // MIME 타입 처리 header.add("Content-Type",
 * Files.probeContentType(file.toPath()));
 * 
 * // 파일 데이터 처리 result = new
 * ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
 * 
 * 
 * } catch (Exception e) { log.error(e.getMessage()); return new
 * ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); }
 * 
 * return result;
 * 
 * } 업로드 파일 삭제
 * 
 * @PostMapping("/removeFile") public ResponseEntity<Boolean> removeFile(String
 * fileName) {
 * 
 * String srcFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
 * 
 * File file = new File(uploadPath + File.separator + srcFileName);
 * 
 * File thumbnail = new File(file.getParent(), "s_" + file.getName());
 * 
 * boolean result = file.delete() && thumbnail.delete();
 * 
 * return new ResponseEntity<>(result, HttpStatus.OK);
 * 
 * }
 * 
 * 
 * 
 * 
 * }
 */
