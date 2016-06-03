package controllers.actor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MessageFolderService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Message;
import domain.MessageFolder;
import forms.MoveMessageForm;

@Controller
@RequestMapping("/message/actor")
public class MessageActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MessageService messageService;
	@Autowired
	private MessageFolderService messageFolderService;
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public MessageActorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int messageFolderId) {
		ModelAndView result;

		if (messageFolderService.isOwner(messageFolderId)) {
			MessageFolder messageFolder;
			Collection<Message> messages;

			messageFolder = messageFolderService.findOne(messageFolderId);
			messages = messageService.findAllByFolder(messageFolder);

			result = new ModelAndView("message/list");
			result.addObject("requestURI", "message/actor/list.do");
			result.addObject("messages", messages);
			result.addObject("messageFolderId", messageFolderId);
		} else {
			result = new ModelAndView("misc/403");
		}
		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/sendMsg", method = RequestMethod.GET)
	public ModelAndView sendMsg() {
		ModelAndView result;
		Message msg;
		MessageFolder messageFolder;
		Actor actor;
		Collection<Actor> recipients;

		actor = actorService.findByPrincipal();
		recipients = actorService.findAllExceptMe();
		messageFolder = messageFolderService.findFolder(actor, "Out box");

		msg = messageService.create(actor, messageFolder);

		result = new ModelAndView("message/create");
		result.addObject("sendMessage", msg);
		result.addObject("recipients", recipients);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@Valid int messageFolderId) {
		ModelAndView result;

		if (messageFolderService.isOwner(messageFolderId)) {
			Message msg;
			MessageFolder messageFolder;
			Actor actor;
			Collection<Actor> recipients;

			actor = actorService.findByPrincipal();
			recipients = actorService.findAllExceptMe();
			messageFolder = messageFolderService.findOne(messageFolderId);

			msg = messageService.create(actor, messageFolder);

			result = new ModelAndView("message/create");
			result.addObject("sendMessage", msg);
			result.addObject("recipients", recipients);
		} else {
			result = new ModelAndView("misc/403");
		}
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "send")
	public ModelAndView save(
			@ModelAttribute(value = "sendMessage") @Valid Message msg,
			BindingResult binding) {
		ModelAndView result;
		Collection<Actor> recipients;
		recipients = actorService.findAllExceptMe();

		if (binding.hasErrors()) {
			result = new ModelAndView("message/create");
			result.addObject("recipients", recipients);
		} else {
			try {
				messageService.sendMessage(msg);
				result = new ModelAndView(
						"redirect:/messageFolder/actor/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView("message/create");
				result.addObject("sendMessage", msg);
				result.addObject("recipients", recipients);
				result.addObject("message", "message.commit.error");
			}
		}

		return result;
	}

	// Details ----------------------------------------------------------------

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int messageId) {
		ModelAndView result;
		Message message;

		message = messageService.findOne(messageId);
		if (messageService.isOwner(message)) {
			result = new ModelAndView("message/details");
			result.addObject("msg", message);
		} else {
			result = new ModelAndView("misc/403");
		}

		return result;
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(
			@ModelAttribute(value = "msg") @Valid Message message,
			BindingResult binding) {
		ModelAndView result;
		MessageFolder messageFolder;
		messageFolder = message.getMessageFolder();

		try {
			if (messageFolder.getName().equals("Trash box")) {
				messageService.delete(message);
				result = new ModelAndView(
						"redirect:../../messageFolder/actor/list.do?messageFolderId="
								+ messageFolder.getId());
			} else {
				messageService.moveToTrashbox(message);
				result = new ModelAndView(
						"redirect:../../messageFolder/actor/list.do?messageFolderId="
								+ messageFolder.getId());
			}
		} catch (Throwable oops) {
			result = new ModelAndView(
					"redirect:../../messageFolder/actor/list.do?messageFolderId="
							+ messageFolder.getId());
		}

		return result;
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST, params = "spam")
	public ModelAndView moveToSpam(
			@ModelAttribute(value = "msg") @Valid Message message,
			BindingResult binding) {
		ModelAndView result;
		Actor actor;
		actor = actorService.findByPrincipal();
		MessageFolder messageFolder;
		messageFolder = message.getMessageFolder();
		MessageFolder spamFolder;
		spamFolder = messageFolderService.findFolder(actor, "Spam box");
		try {
			if (!messageFolder.getName().equals("Spam box")) {
				messageService.moveToFolder(message, spamFolder);
			}
			result = new ModelAndView(
					"redirect:../../messageFolder/actor/list.do?messageFolderId="
							+ messageFolder.getId());
		} catch (Throwable oops) {
			result = new ModelAndView(
					"redirect:../../messageFolder/actor/list.do?messageFolderId="
							+ messageFolder.getId());
		}

		return result;
	}

	// MoveToFolder -----------------------------------------------------------

	@RequestMapping(value = "/moveToFolder", method = RequestMethod.GET)
	public ModelAndView moveToFolder(@RequestParam int messageId) {
		ModelAndView result;
		Message message;
		Collection<MessageFolder> messageFolders;
		MoveMessageForm moveMessageForm = new MoveMessageForm();

		message = messageService.findOne(messageId);
		messageFolders = messageFolderService.findByActor();

		moveMessageForm.setMessage(message);

		result = new ModelAndView("message/move");
		result.addObject("moveMessageForm", moveMessageForm);
		result.addObject("messageFolders", messageFolders);

		return result;
	}

	@RequestMapping(value = "/moveToFolder", method = RequestMethod.POST, params = "save")
	public ModelAndView moveToFolder(@Valid MoveMessageForm moveMessageForm,
			BindingResult binding) {
		ModelAndView result;

		try {
			if(messageService.isOwnerFolder(moveMessageForm.getMessageFolder())){
				moveMessageForm.getMessageFolder();
				result = new ModelAndView(
						"redirect:../../messageFolder/actor/list.do");
			}else{
				result = new ModelAndView("misc/403");
				
			}
					
		} catch (Throwable oops) {
			Collection<MessageFolder> messageFolders = messageFolderService
					.findByActor();
			result = new ModelAndView("message/move");
			result.addObject("moveMessageForm", moveMessageForm);
			result.addObject("messageFolders", messageFolders);
			result.addObject("message", "message.move.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

}
